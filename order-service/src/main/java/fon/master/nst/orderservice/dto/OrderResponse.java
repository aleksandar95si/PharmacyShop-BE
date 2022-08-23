package fon.master.nst.orderservice.dto;

import fon.master.nst.orderservice.model.OrderStatus;

public class OrderResponse {

    private OrderStatus orderStatus;

    public OrderResponse(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
