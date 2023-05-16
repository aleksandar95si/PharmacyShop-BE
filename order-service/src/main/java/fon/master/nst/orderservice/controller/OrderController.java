package fon.master.nst.orderservice.controller;

import fon.master.nst.orderservice.dto.OrderRequest;
import fon.master.nst.orderservice.dto.OrderResponse;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.service.OrderService;
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

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public ResponseEntity<OrderResponse> getOrderItemsAndSendEmail(@RequestBody ShoppingCart shoppingCart) {

        OrderResponse orderResponse = orderService.createOrder(shoppingCart);
        return ResponseEntity.ok(orderResponse);
    }
}
