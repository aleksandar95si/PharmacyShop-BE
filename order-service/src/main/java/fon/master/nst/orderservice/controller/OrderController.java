package fon.master.nst.orderservice.controller;

import fon.master.nst.orderservice.dto.OrderResponse;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.service.impl.OrderServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderServiceImpl orderServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/submit")
    public ResponseEntity<OrderResponse> getOrderItemsAndSendEmail(@RequestBody ShoppingCart shoppingCart) {
        OrderResponse orderResponse = orderServiceImpl.createOrder(shoppingCart);
        return ResponseEntity.ok(orderResponse);
    }
}
