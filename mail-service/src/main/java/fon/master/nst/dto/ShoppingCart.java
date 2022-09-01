package fon.master.nst.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart implements Serializable {

    private Long cartId;
    private String username;
    private Long bill;
    private List<CartItem> cartItem = new ArrayList<>();

    public ShoppingCart() {

    }

    public ShoppingCart(String username) {
        super();
        this.username = username;
    }

    public ShoppingCart addCartItem(CartItem cartItem) {
        this.cartItem.add(cartItem);
        cartItem.setShoppingCart(this);
        return this;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getBill() {
        return bill;
    }

    public void setBill(Long bill) {
        this.bill = bill;
    }

    public List<CartItem> getCartItems() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "cartId=" + cartId +
                ", username='" + username + '\'' +
                ", bill=" + bill +
                ", cartItem=" + cartItem +
                '}';
    }
}
