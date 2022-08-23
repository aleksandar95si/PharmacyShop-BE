package fon.master.nst.paymentservice.service.impl;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;
import fon.master.nst.paymentservice.model.Customer;
import fon.master.nst.paymentservice.model.Payment;
import fon.master.nst.paymentservice.model.PaymentStatus;
import fon.master.nst.paymentservice.repository.CustomerRepository;
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
    private CustomerRepository customerRepository;

    @Override
    public PaymentResponse processPayment(PaymentRequest paymentRequest) {

        PaymentResponse paymentResponse = new PaymentResponse();

        Payment payment = new Payment();
        payment.setUsername(paymentRequest.getUsername());
        payment.setAmount(paymentRequest.getAmount());

        Customer customer;

        try {

            customer = customerRepository.findByUsername(paymentRequest.getUsername());

        } catch (Exception e) {

            paymentResponse.setPaymentStatus(PaymentStatus.FAILED);

            LOGGER.error("An error has occurred while trying to get Customer's credit info. Error: " + e.getMessage());

            return paymentResponse;
        }

        if (customer.getCredit() >= paymentRequest.getAmount()) {

            customer.setCredit(customer.getCredit() - paymentRequest.getAmount());

            try {

                customerRepository.save(customer);

                payment.setPaymentStatus(PaymentStatus.PAID);

                paymentResponse.setPaymentStatus(PaymentStatus.PAID);

                LOGGER.info("Payment has been successfully paid");

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

}
