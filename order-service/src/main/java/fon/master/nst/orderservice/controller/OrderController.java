package fon.master.nst.orderservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.orderservice.service.impl.EmailServiceImpl;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @PostMapping("/submit")
    public ResponseEntity getOrderItemsAndSendEmail() {
        logger.info("User clicked on getOrderItemsAndSendEmail method");
        emailServiceImpl.sendPDFReport(""); //hardkotovati mejl primaoca radi testiranja
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
