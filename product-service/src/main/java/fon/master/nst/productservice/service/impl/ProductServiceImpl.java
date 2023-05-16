package fon.master.nst.productservice.service.impl;

import fon.master.nst.productservice.event.OrderEvent;
import fon.master.nst.productservice.exception.ApiException;
import fon.master.nst.productservice.model.OrderStatus;
import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.repository.ProductRepository;
import fon.master.nst.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByGroupName(String name) {

        List<Product> listOfProductsByGroupName = productRepository.findByProductCategoryName(name);

        if (listOfProductsByGroupName == null || listOfProductsByGroupName.isEmpty()) {
            logger.error("Invalid group name!");
            throw new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
        }
        logger.info("List of products was showed");
        return listOfProductsByGroupName;
    }

    public Product findByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException("There is no product with id " + productId, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name()));
    }

    @Override
    public boolean updateProductsTotalAmount(List<Product> products) {
        try {
            productRepository.saveAll(products);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to update products total amount. Error: " + e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findProductsById(List<Long> ids) {

        List<Product> products = null;

        try {
            products = productRepository.findAllById(ids);
        } catch (Exception e) {
            logger.error("An error has occurred while trying to find all products by id. Error: " + e.getMessage());
        }
        return products;
    }

    private void reserveProducts(OrderEvent orderEvent) {

        Map<Long, Long> orderedProducts = new HashMap<>();

        orderEvent.getShoppingCart().getCartItems().forEach(cartItem -> {
            orderedProducts.computeIfAbsent(cartItem.getProductId(), amount -> 1L);
            orderedProducts.computeIfPresent(cartItem.getProductId(), (id, amount) -> amount + 1);
        });

        List<Product> products = findProductsById(new ArrayList<>(orderedProducts.keySet()));

        List<Product> updatedProducts = products.stream()
                .filter(product -> product.getAvailableAmount() >= orderedProducts.get(product.getProductId()))
                .peek(product -> {
                    product.setAvailableAmount(product.getAvailableAmount() - orderedProducts.get(product.getProductId()));
                    product.setReservedAmount(orderedProducts.get(product.getProductId()));
                })
                .collect(Collectors.toList());

        if (updatedProducts.size() == orderedProducts.size()) {
            try {
                productRepository.saveAll(updatedProducts);
                orderEvent.setOrderStatus(OrderStatus.PRODUCT_RESERVED);
            } catch (Exception e) {
                orderEvent.setOrderStatus(OrderStatus.PRODUCT_FAILED);
            }
        } else {
            orderEvent.setOrderStatus(OrderStatus.PRODUCT_OUT_OF_STOCK);
        }

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
                // TODO: 4/15/2023 retry mechanism
            }
        });
    }

    private void removeProductReservation(OrderEvent orderEvent) {

        Map<Long, Long> orderedProducts = new HashMap<>();

        orderEvent.getShoppingCart().getCartItems().forEach(cartItem -> {
            orderedProducts.computeIfAbsent(cartItem.getProductId(), amount -> 1L);
            orderedProducts.computeIfPresent(cartItem.getProductId(), (id, amount) -> amount + 1);
        });

        List<Product> products = findProductsById(new ArrayList<>(orderedProducts.keySet()));

        products.forEach(product -> {
            product.setReservedAmount(product.getReservedAmount() - orderedProducts.get(product.getProductId()));
            product.setAvailableAmount(product.getAvailableAmount() + orderedProducts.get(product.getAvailableAmount()));
        });

        try {
            productRepository.saveAll(products);
        } catch (Exception e) {

        }
    }

    @KafkaListener(topics = "order-topic", groupId = "product-service-group")
    public void receive(@Payload OrderEvent orderEvent, @org.springframework.messaging.handler.annotation.Headers Map<String, Object> headers) {
        if (headers.containsKey("orderStatus")) {
            String orderStatus = new String((byte[]) headers.get("orderStatus"));
            if (orderStatus.equals(OrderStatus.CREATED.name())) {
                reserveProducts(orderEvent);
            } else if (orderStatus.equals(OrderStatus.CUSTOMER_FAILED.name())
                    || orderStatus.equals(OrderStatus.PAYMENT_FAILED.name())
                    || orderStatus.equals(OrderStatus.PAYMENT_OUT_OF_MONEY.name())) {
                removeProductReservation(orderEvent);
            } else {
                // order status ignored
            }
        } else {
        }
    }

}
