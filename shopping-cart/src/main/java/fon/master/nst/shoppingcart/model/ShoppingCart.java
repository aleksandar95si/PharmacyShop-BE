package fon.master.nst.shoppingcart.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long cartId;

    @Column(name = "username")
    private String username;

    @Column(name = "bill")
    private Long bill;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
    @JsonManagedReference
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

    public List<CartItem> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItem> cartItem) {
        this.cartItem = cartItem;
    }

    @Override
    public String toString() {
        return "ShoppingCart [cartId=" + cartId + ", username=" + username + ", cartItem=" + cartItem.size() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShoppingCart other = (ShoppingCart) obj;
        if (cartId == null) {
            if (other.cartId != null)
                return false;
        } else if (!cartId.equals(other.cartId))
            return false;
        return true;
    }

}
