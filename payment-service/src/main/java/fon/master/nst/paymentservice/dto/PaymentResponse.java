package fon.master.nst.paymentservice.dto;

import fon.master.nst.paymentservice.model.PaymentStatus;

public class PaymentResponse {

    private PaymentStatus paymentStatus;

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
