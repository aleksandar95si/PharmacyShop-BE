package fon.master.nst.paymentservice.service;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;

public interface PaymentService {

    PaymentResponse processPayment(PaymentRequest paymentRequest);

    PaymentResponse rollbackPayment(PaymentRequest paymentRequest);

}
