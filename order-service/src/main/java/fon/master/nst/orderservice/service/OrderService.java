package fon.master.nst.orderservice.service;

import fon.master.nst.orderservice.dto.OrderResponse;
import fon.master.nst.orderservice.dto.ShoppingCart;
import fon.master.nst.orderservice.model.OrderStatus;

public interface OrderService {

    OrderResponse createOrder(ShoppingCart shoppingCart);

}
