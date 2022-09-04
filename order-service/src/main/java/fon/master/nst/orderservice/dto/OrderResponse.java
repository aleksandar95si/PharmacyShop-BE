package fon.master.nst.orderservice.dto;

import fon.master.nst.orderservice.model.OrderStatus;

public class OrderResponse {

    private Long orderId;

    private OrderStatus orderStatus;

    public OrderResponse(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
