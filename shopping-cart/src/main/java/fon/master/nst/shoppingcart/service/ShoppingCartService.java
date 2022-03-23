package fon.master.nst.shoppingcart.service;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;

public interface ShoppingCartService {

    void addItem(Long productId);

    ShoppingCart getShoppingCart();

    void removeCartItem(Long itemId);

    ShoppingCart getCartById(Long cartId);

    void deleteCart(Long cartId);

    CartItem getItem(Long cartId);

}
