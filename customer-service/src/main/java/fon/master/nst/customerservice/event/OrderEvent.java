package fon.master.nst.customerservice.event;

import fon.master.nst.customerservice.dto.ShoppingCart;
import fon.master.nst.customerservice.model.Customer;
import fon.master.nst.customerservice.model.OrderStatus;
import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {

    private Long orderId;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
}
