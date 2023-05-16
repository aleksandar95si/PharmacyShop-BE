package fon.master.nst.orderservice.event;

import fon.master.nst.orderservice.dto.Customer;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.model.OrderStatus;
import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {

    private Long orderId;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
}
