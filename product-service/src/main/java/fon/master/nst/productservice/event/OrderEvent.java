package fon.master.nst.productservice.event;

import fon.master.nst.productservice.dto.Customer;
import fon.master.nst.productservice.dto.ShoppingCart;
import fon.master.nst.productservice.model.OrderStatus;
import lombok.Data;

import java.util.Map;

@Data
public class OrderEvent {

    private Long orderId;
    private Customer customer;
    private ShoppingCart shoppingCart;
    private OrderStatus orderStatus;
}
