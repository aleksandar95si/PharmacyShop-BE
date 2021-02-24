package fon.master.nst.shoppingcart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.dto.User;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Long cartId;
	
	@Column(name="user_id")
	private Long userId;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
	private List<CartItem> cartItem=new ArrayList<>();
	
	
	public ShoppingCart() {
		super();
	}
	public ShoppingCart(Long userId) {
		super();
		this.userId = userId;
	}
	public Long getCartId() {
		return cartId;
	}
	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}
	public Long getUser() {
		return userId;
	}
	public void setUser(Long userId) {
		this.userId = userId;
	}
	public List<CartItem> getCartItem() {
		return getCartItem();
	}
	public void setCartItem(List<CartItem> cartItem) {
		this.cartItem = cartItem;
	}
	
	

}
