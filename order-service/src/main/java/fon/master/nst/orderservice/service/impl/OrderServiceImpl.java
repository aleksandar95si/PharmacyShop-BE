package fon.master.nst.orderservice.service.impl;

import feign.FeignException;
import fon.master.nst.orderservice.client.MailClient;
import fon.master.nst.orderservice.client.PaymentClient;
import fon.master.nst.orderservice.dto.*;
import fon.master.nst.orderservice.model.*;
import fon.master.nst.orderservice.repository.CustomerRepository;
import fon.master.nst.orderservice.repository.OrderRepository;
import fon.master.nst.orderservice.repository.ProductRepository;
import fon.master.nst.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private MailClient mailClient;
    @Autowired
    private PaymentClient paymentClient;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    public OrderResponse createOrder(ShoppingCart shoppingCart) {

        Order order = new Order();
        order.setUsername(shoppingCart.getUsername());

        List<Long> productIds = new ArrayList<>();

        Map<Long, Long> purchasedAmountOfItems = new HashMap<>();

        shoppingCart.getCartItems().forEach(cartItem -> {
            productIds.add(cartItem.getProductId());
            purchasedAmountOfItems.computeIfAbsent(cartItem.getProductId(), amount -> 0L);
            purchasedAmountOfItems.computeIfPresent(cartItem.getProductId(), (id, amount) -> amount + 1);
            System.out.println(purchasedAmountOfItems);
        });

        List<Product> orderedProductsPO;
        List <Product> orderedProducts = new ArrayList<>();

        try {
            orderedProductsPO = productRepository.findAllById(productIds);
            orderedProductsPO.forEach(productPO -> orderedProducts.add(convertPersistentProductObjectToFreeObject(productPO)));
        } catch (Exception e) {
            logger.error("An error has occurred while trying to save order info (status: FAILED)");
            return new OrderResponse(OrderStatus.FAILED);
        }

        for (Product product : orderedProducts) {

            Long productTotalAmount = product.getTotalAmount();

            Long productOrderedAmount = purchasedAmountOfItems.get(product.getProductId());

            if (productTotalAmount < productOrderedAmount) {

                order.setOrderStatus(OrderStatus.OUT_OF_STOCK);

                saveOrder(order);

                return new OrderResponse(OrderStatus.OUT_OF_STOCK);
            }

            product.setTotalAmount(productTotalAmount - productOrderedAmount);
        }

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUsername(shoppingCart.getUsername());
        paymentRequest.setAmount(shoppingCart.getBill());

        PaymentResponse paymentResponse;
        try {
            paymentResponse = paymentClient.processPayment(paymentRequest);
        } catch (FeignException feignException) {
            logger.error("An error has occurred while trying to call Payment service. Error: " + feignException.getMessage());
            return new OrderResponse(OrderStatus.FAILED);
        }

        if (PaymentStatus.REJECTED.equals(paymentResponse.getPaymentStatus())) {

            logger.error("Payment has been rejected due to exceeding the credit limit");
            order.setOrderStatus(OrderStatus.REJECTED);
            saveOrder(order);

            return new OrderResponse(OrderStatus.REJECTED);
        } else if (PaymentStatus.FAILED.equals(paymentResponse.getPaymentStatus())) {

            logger.error("Payment service has failed to process payment");

            return new OrderResponse(OrderStatus.FAILED);
        }

        try {
            productRepository.saveAll(orderedProducts);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to update product's stock");
        }

        order.setOrderStatus(OrderStatus.COMPLETED);
        saveOrder(order);

        Customer customer;

        try {
            customer = customerRepository.findByUsername(shoppingCart.getUsername());
        } catch (Exception exception) {
            logger.error("An error has occurred while trying to fetch Customer's data. Error: " + exception.getLocalizedMessage());
            customer = null;
        }

        if (customer != null) {

            logger.info("Order microservice calls Mail microservice to send pdf report to the customer's email");

            try {
                MailRequest mailRequest = new MailRequest(customer.getEmail(), shoppingCart);
                mailClient.send(mailRequest);
            } catch (FeignException feignException) {
                logger.error("An error has occurred while trying to call Mail service: " + feignException.getMessage());
            }
        }

        return new OrderResponse(OrderStatus.COMPLETED);
    }

    private void saveOrder(Order order) {
        try {
            orderRepository.save(order);
        } catch (Exception e) {
            logger.error(String.format("An error has occurred while trying to save order info (status: %s)", order.getOrderStatus()));
        }
    }

    private Product convertPersistentProductObjectToFreeObject(Product productPO) {

        Product productFO = new Product();
        productFO.setProductId(productPO.getProductId());
        productFO.setName(productPO.getName());
        productFO.setPrice(productPO.getPrice());
        productFO.setTotalAmount(productPO.getTotalAmount());
        productFO.setProductImgPath(productPO.getProductImgPath());
        productFO.setProductCategory(productPO.getProductCategory());

        return productFO;
    }
}
