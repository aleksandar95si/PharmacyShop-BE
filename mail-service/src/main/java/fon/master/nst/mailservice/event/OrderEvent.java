package fon.master.nst.mailservice.event;

import fon.master.nst.mailservice.dto.Customer;
import fon.master.nst.mailservice.dto.ShoppingCart;
import fon.master.nst.mailservice.model.OrderStatus;
import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {

    private Long orderId;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
}
