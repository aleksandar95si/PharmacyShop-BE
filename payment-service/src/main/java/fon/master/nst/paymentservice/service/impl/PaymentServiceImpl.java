package fon.master.nst.paymentservice.service.impl;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;
import fon.master.nst.paymentservice.model.Balance;
import fon.master.nst.paymentservice.model.Payment;
import fon.master.nst.paymentservice.model.PaymentStatus;
import fon.master.nst.paymentservice.repository.BalanceRepository;
import fon.master.nst.paymentservice.repository.PaymentRepository;
import fon.master.nst.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private BalanceRepository balanceRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = new PaymentResponse();

        Payment payment = new Payment();
        payment.setUsername(paymentRequest.getUsername());
        payment.setAmount(paymentRequest.getAmount());

        Balance balance = balanceRepository.findByUsername(paymentRequest.getUsername());

        if (balance.getCredit() >= paymentRequest.getAmount()) {

            balance.setCredit(balance.getCredit() - paymentRequest.getAmount());

            try {

                balanceRepository.save(balance);

                payment.setPaymentStatus(PaymentStatus.PAID);

                paymentResponse.setPaymentStatus(PaymentStatus.PAID);

                LOGGER.info("Payment request has been successfully processed");

            } catch (Exception e) {

                payment.setPaymentStatus(PaymentStatus.FAILED);

                paymentResponse.setPaymentStatus(PaymentStatus.FAILED);

                LOGGER.error("An error has occurred while trying to update Customer's credit info. Error: " + e.getMessage());

            }

        } else {

            payment.setPaymentStatus(PaymentStatus.REJECTED);

            paymentResponse.setPaymentStatus(PaymentStatus.REJECTED);

            LOGGER.info("There is no enough credit on Customer's account");

            try {

                paymentRepository.save(payment);

            } catch (Exception e) {

                LOGGER.error("An error has occurred while trying to save payment. Error: " + e.getMessage());

            }

        }

        return paymentResponse;
    }

    @Override
    public PaymentResponse rollbackPayment(PaymentRequest paymentRequest) {

        Balance balance;

        try {
            balance = balanceRepository.findByUsername(paymentRequest.getUsername());
        } catch (Exception e) {
            return new PaymentResponse(PaymentStatus.REFUND);
        }

        balance.setCredit(balance.getCredit() + paymentRequest.getAmount());

        Payment payment = new Payment();

        payment.setPaymentStatus(PaymentStatus.REFUND);
        payment.setAmount(payment.getAmount());
        payment.setUsername(payment.getUsername());

        try {
            paymentRepository.save(payment);
            LOGGER.info("Refund request has been successfully processed");
        } catch (Exception e) {

        }
        return new PaymentResponse(PaymentStatus.REFUND);
    }

}
