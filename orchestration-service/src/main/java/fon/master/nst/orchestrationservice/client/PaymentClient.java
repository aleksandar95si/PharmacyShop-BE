package fon.master.nst.orchestrationservice.client;

import fon.master.nst.orchestrationservice.config.FeignAuthConfiguration;
import fon.master.nst.orchestrationservice.dto.PaymentRequest;
import fon.master.nst.orchestrationservice.dto.PaymentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PAYMENT-SERVICE", configuration = FeignAuthConfiguration.class)
public interface PaymentClient {
    
    @PostMapping("/payments-api/payments")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);

    @PutMapping("/payments-api/payments/rollback")
    PaymentResponse rollbackPayment(@RequestBody PaymentRequest paymentRequest);
}
