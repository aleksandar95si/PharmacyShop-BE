package fon.master.nst.paymentservice.controller;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;
import fon.master.nst.paymentservice.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = paymentService.processPayment(paymentRequest);

        return ResponseEntity.ok(paymentResponse);
    }

}
