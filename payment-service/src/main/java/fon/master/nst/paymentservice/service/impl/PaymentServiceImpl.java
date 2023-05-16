package fon.master.nst.paymentservice.service.impl;

import fon.master.nst.paymentservice.dto.PaymentRequest;
import fon.master.nst.paymentservice.dto.PaymentResponse;
import fon.master.nst.paymentservice.event.OrderEvent;
import fon.master.nst.paymentservice.model.Balance;
import fon.master.nst.paymentservice.model.OrderStatus;
import fon.master.nst.paymentservice.model.Payment;
import fon.master.nst.paymentservice.model.PaymentStatus;
import fon.master.nst.paymentservice.repository.BalanceRepository;
import fon.master.nst.paymentservice.repository.PaymentRepository;
import fon.master.nst.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    private static Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

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

    @KafkaListener(topics = "order-topic", groupId = "payment-service-group")
    public void receive(@Payload OrderEvent orderEvent, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        if (headers.containsKey("orderStatus")) {
            String orderStatus = new String((byte[]) headers.get("orderStatus"));
            if (orderStatus.equals(OrderStatus.CUSTOMER_PAYMENT_PREPARED.name())) {
                processPayment(orderEvent);
            }
        }
    }

    void processPayment(OrderEvent orderEvent) {

        Map<Long, Long> orderedProducts = new HashMap<>();

        orderEvent.getShoppingCart().getCartItems().forEach(cartItem -> {
            orderedProducts.computeIfAbsent(cartItem.getProductId(), amount -> 1L);
            orderedProducts.computeIfPresent(cartItem.getProductId(), (id, amount) -> amount + 1);
        });

        Balance balance = balanceRepository.findByAccountNumber(orderEvent.getCustomer().getCurrentAccount());

        AtomicReference<Long> orderedProductsPrice = new AtomicReference<>(0L);

        orderedProducts.forEach((productId, price) -> orderedProductsPrice.set(orderedProductsPrice.get() + price));

        if (balance.getCredit() < orderedProductsPrice.get()) {
            orderEvent.setOrderStatus(OrderStatus.PAYMENT_OUT_OF_MONEY);
        } else {

            balance.setCredit(balance.getCredit() - orderedProductsPrice.get());
            Payment payment = new Payment();
            payment.setPaymentStatus(PaymentStatus.PAID);
            payment.setAmount(orderedProductsPrice.get());
            payment.setUsername(orderEvent.getCustomer().getUsername());

            try {
                balanceRepository.save(balance);
                paymentRepository.save(payment);
                orderEvent.setOrderStatus(OrderStatus.PAYMENT_SUCCESSFUL);
            } catch (Exception exception) {
                log.error(String.format("An error has occurred while trying to update balance or to save payment object. Error message: %s", exception.getMessage()));
                orderEvent.setOrderStatus(OrderStatus.FAILED);
            }
        }

        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("orderStatus", orderEvent.getOrderStatus().name().getBytes()));

        ProducerRecord<String, OrderEvent> record = new ProducerRecord<String, OrderEvent>("order-topic", null, null, orderEvent, headers);

        ListenableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, OrderEvent> result) {
                log.info("Sent message={} to topic={} with offset={}", orderEvent, result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("Unable to send message={} due to : {}", orderEvent, ex.getMessage());
                // TODO: 4/15/2023 retry mechanism
            }
        });
    }

}
