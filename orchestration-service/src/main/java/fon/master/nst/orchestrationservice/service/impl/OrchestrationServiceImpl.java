package fon.master.nst.orchestrationservice.service.impl;

import feign.FeignException;
import fon.master.nst.orchestrationservice.client.CustomerClient;
import fon.master.nst.orchestrationservice.client.MailClient;
import fon.master.nst.orchestrationservice.client.PaymentClient;
import fon.master.nst.orchestrationservice.client.ProductClient;
import fon.master.nst.orchestrationservice.dto.*;
import fon.master.nst.orchestrationservice.service.OrchestrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrchestrationServiceImpl implements OrchestrationService {

    private static final Logger logger = LoggerFactory.getLogger(OrchestrationServiceImpl.class);

    @Autowired
    private MailClient mailClient;
    @Autowired
    private ProductClient productClient;
    @Autowired
    private CustomerClient customerClient;
    @Autowired
    private PaymentClient paymentClient;

    @Override
    public OrderResponse processOrderRequest(OrderRequest orderRequest) {
        List<Long> productIds = new ArrayList<>();

        Map<Long, Long> purchasedAmountOfItems = new HashMap<>();

        orderRequest.getShoppingCart().getCartItems().forEach(cartItem -> {
            productIds.add(cartItem.getProductId());
            purchasedAmountOfItems.computeIfAbsent(cartItem.getProductId(), amount -> 0L);
            purchasedAmountOfItems.computeIfPresent(cartItem.getProductId(), (id, amount) -> amount + 1);
        });

        List <Product> orderedProducts;

        try {
            orderedProducts = productClient.getProductsById(productIds);
        } catch (FeignException e) {
            logger.error("An error has occurred while trying to get fetch products data. Error: " + e.getMessage());
            return new OrderResponse(orderRequest.getOrderId(), OrderStatus.FAILED);
        }

        for (Product product : orderedProducts) {

            Long productTotalAmount = product.getTotalAmount();

            Long productOrderedAmount = purchasedAmountOfItems.get(product.getProductId());

            if (productTotalAmount < productOrderedAmount) {
                return new OrderResponse(orderRequest.getOrderId(), OrderStatus.OUT_OF_STOCK);
            }

            product.setTotalAmount(productTotalAmount - productOrderedAmount);
        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUsername(orderRequest.getShoppingCart().getUsername());
        paymentRequest.setAmount(orderRequest.getShoppingCart().getBill());

        PaymentResponse paymentResponse;
        try {
            paymentResponse = paymentClient.processPayment(paymentRequest);
        } catch (FeignException e) {
            logger.error("An error has occurred while trying to call Payment service. Error: " + e.getMessage());
            return new OrderResponse(orderRequest.getOrderId(), OrderStatus.FAILED);
        }

        if (PaymentStatus.REJECTED.equals(paymentResponse.getPaymentStatus())) {

            logger.error("Payment has been rejected due to exceeding the credit limit");

            return new OrderResponse(orderRequest.getOrderId(), OrderStatus.REJECTED);
        } else if (PaymentStatus.FAILED.equals(paymentResponse.getPaymentStatus())) {

            logger.error("Payment service has failed to process payment");

            return new OrderResponse(orderRequest.getOrderId(), OrderStatus.FAILED);
        }

        try {
            productClient.updateProductsTotalAmount(orderedProducts);
        } catch (FeignException e) {
            logger.error("An error has occurred while trying to update product's stock. Error: " + e.getMessage());
            paymentClient.rollbackPayment(paymentRequest);
            return new OrderResponse(orderRequest.getOrderId(), OrderStatus.FAILED);
        }

        Customer customer;

        try {
            customer = customerClient.getCustomerByUsername(AuthService.getCurrentUsersUsername());
        } catch (FeignException e) {
            logger.error("An error has occurred while trying to fetch Customer's data. Error: " + e.getLocalizedMessage());
            customer = null;
        }

        if (customer != null) {

            logger.info("Order microservice calls Mail microservice to send pdf report to the customer's email");

            try {
                MailRequest mailRequest = new MailRequest(customer.getEmail(), orderRequest.getShoppingCart());
                mailClient.send(mailRequest);
            } catch (FeignException feignException) {
                logger.error("An error has occurred while trying to call Mail service: " + feignException.getMessage());
            }
        }

        return new OrderResponse(orderRequest.getOrderId(), OrderStatus.COMPLETED);

    }
}
