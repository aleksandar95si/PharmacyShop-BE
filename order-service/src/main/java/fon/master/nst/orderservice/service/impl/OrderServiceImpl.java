package fon.master.nst.orderservice.service.impl;

import fon.master.nst.orderservice.dto.Customer;
import fon.master.nst.orderservice.dto.OrderRequest;
import fon.master.nst.orderservice.dto.OrderResponse;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.event.OrderEvent;
import fon.master.nst.orderservice.model.Order;
import fon.master.nst.orderservice.model.OrderStatus;
import fon.master.nst.orderservice.repository.OrderRepository;
import fon.master.nst.orderservice.service.OrderService;
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

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderResponse  createOrder(ShoppingCart shoppingCart) {

        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);
        order.setUsername(shoppingCart.getUsername());

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to save order to database. Error message: " + e.getMessage());
        }

        OrderEvent orderEvent = new OrderEvent();
        orderEvent.setOrderId(order.getOrderId());
        Customer customer = new Customer();
        customer.setUsername(shoppingCart.getUsername());
        orderEvent.setCustomer(customer);
        orderEvent.setShoppingCart(shoppingCart);
        orderEvent.setOrderStatus(OrderStatus.CREATED);
        orderEvent.setShoppingCart(shoppingCart);

        Headers headers = new RecordHeaders();
        headers.add(new RecordHeader("orderStatus", orderEvent.getOrderStatus().name().getBytes()));

        ProducerRecord<String, OrderEvent> record = new ProducerRecord<String, OrderEvent>("order-topic", null, null, orderEvent, headers);

        ListenableFuture<SendResult<String, OrderEvent>> future = kafkaTemplate.send(record);
        future.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, OrderEvent> result) {
                logger.info("Sent message={} to topic={} with offset={}", orderEvent, result.getRecordMetadata().topic(), result.getRecordMetadata().offset());
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.error("Unable to send message={} due to : {}", orderEvent, ex.getMessage());
            }
        });

        return new OrderResponse(order.getOrderId(), order.getOrderStatus());
    }

    private void updateOrderStatus(OrderEvent orderEvent) {
        Order order = new Order();
        order.setOrderId(orderEvent.getOrderId());
        order.setOrderStatus(orderEvent.getOrderStatus());
        order.setUsername(orderEvent.getShoppingCart().getUsername());

        try {
            order = orderRepository.save(order);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to save order to database. Error message: " + e.getMessage());
        }
    };

    @KafkaListener(topics = "order-topic", groupId = "order-service-group")
    public void receive(@Payload OrderEvent orderEvent, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        // Proveri vrednost orderStatus header-a
        if (headers.containsKey("orderStatus")) {
            String orderStatus = new String((byte[]) headers.get("orderStatus"));
            if (!orderStatus.equals(OrderStatus.CREATED.name())) {
                // logika za obradu poruke
                updateOrderStatus(orderEvent);
            } else {
                // ne želimo da deserijalizujemo poruku koja nije za nas, zato preskačemo obradu
            }
        } else {
        }
    }
}
