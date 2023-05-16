package fon.master.nst.paymentservice.event;

import fon.master.nst.paymentservice.dto.Customer;
import fon.master.nst.paymentservice.dto.ShoppingCart;
import fon.master.nst.paymentservice.model.OrderStatus;
import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {

    private Long orderId;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
}
