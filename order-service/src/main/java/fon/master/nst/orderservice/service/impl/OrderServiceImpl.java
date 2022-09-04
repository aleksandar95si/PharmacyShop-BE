package fon.master.nst.orderservice.service.impl;

import feign.FeignException;
import fon.master.nst.orderservice.client.OrchestrationClient;
import fon.master.nst.orderservice.dto.*;
import fon.master.nst.orderservice.model.Order;
import fon.master.nst.orderservice.model.OrderStatus;
import fon.master.nst.orderservice.repository.OrderRepository;
import fon.master.nst.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrchestrationClient orchestrationClient;
    @Autowired
    private OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderResponse processOrderRequest(ShoppingCart shoppingCart) {

        Order order = new Order();
        order.setUsername(shoppingCart.getUsername());
        order.setOrderStatus(OrderStatus.CREATED);

        order = saveOrder(order);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrderId(order.getOrderId());
        orderRequest.setOrderStatus(OrderStatus.CREATED);
        orderRequest.setShoppingCart(shoppingCart);

        OrderResponse orderResponse;
        try {
            orderResponse = orchestrationClient.processOrderRequest(orderRequest);
        } catch (FeignException e) {
            logger.info("An error has occurred while trying to call orchestration service. Error: " + e.getMessage());
            saveOrder(order);
            return new OrderResponse(order.getOrderId(), OrderStatus.FAILED);
        }

        order.setOrderStatus(orderResponse.getOrderStatus());
        saveOrder(order);

        return new OrderResponse(order.getOrderId(), orderResponse.getOrderStatus());
    }

    private Order saveOrder(Order order) {
        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            logger.error(String.format("An error has occurred while trying to save order info (status: %s)", order.getOrderStatus()));
        }
        return order;
    }
}
