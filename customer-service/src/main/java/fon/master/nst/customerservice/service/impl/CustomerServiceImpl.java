package fon.master.nst.customerservice.service.impl;

import fon.master.nst.customerservice.event.OrderEvent;
import fon.master.nst.customerservice.model.Customer;
import fon.master.nst.customerservice.model.OrderStatus;
import fon.master.nst.customerservice.repository.CustomerRepository;
import fon.master.nst.customerservice.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.Map;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Override
    public Customer saveUser(Customer customer) {

        Customer savedCustomer;

        try {

            log.info("Trying to save new customer...");

            savedCustomer = customerRepository.save(customer);

            log.info("The new Customer saved!");

        } catch (Exception e) {

            log.error("An error has occurred while trying to save Customer");

            savedCustomer = null;
        }

        return savedCustomer;
    }

    @Override
    public Customer getCustomerByUsername(String username) {

        Customer customer;

        try {

            customer = customerRepository.findByUsername(username);

        } catch (Exception e) {

            log.error("An error has occurred while trying to get Customer's credit info");

            customer = null;
        }

        return customer;
    }

    @KafkaListener(topics = "order-topic", groupId = "customer-service-group")
    public void receive(@Payload OrderEvent orderEvent, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        if (headers.containsKey("orderStatus")) {
            String orderStatus = new String((byte[]) headers.get("orderStatus"));
            if (orderStatus.equals(OrderStatus.PRODUCT_RESERVED.name())) {
                checkCustomersAccount(orderEvent);
            }
        }
    }

    private void checkCustomersAccount(OrderEvent orderEvent) {
        // find customer ID
        Customer customer = customerRepository.findByUsername(orderEvent.getCustomer().getUsername());
        if (customer != null) {
            orderEvent.setCustomer(customer);
            orderEvent.setOrderStatus(OrderStatus.CUSTOMER_PAYMENT_PREPARED);

        } else {
            orderEvent.setOrderStatus(OrderStatus.CUSTOMER_INCOMPLETE_DATA);
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