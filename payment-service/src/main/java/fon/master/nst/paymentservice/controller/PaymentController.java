package fon.master.nst.paymentservice.controller;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;
import fon.master.nst.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }

    @PutMapping("/rollback")
    public ResponseEntity<PaymentResponse> rollbackPayment(@RequestBody PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = paymentService.rollbackPayment(paymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }

}
