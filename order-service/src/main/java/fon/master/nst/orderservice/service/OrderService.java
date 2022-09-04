package fon.master.nst.orderservice.service;

import fon.master.nst.orderservice.dto.OrderResponse;
import fon.master.nst.orderservice.dto.ShoppingCart;

public interface OrderService {

    OrderResponse processOrderRequest(ShoppingCart shoppingCart);

}
