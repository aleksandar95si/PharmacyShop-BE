package fon.master.nst.shoppingcart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.dto.User;

@Entity
@Table(name = "shopping_cart")
public class ShoppingCart implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cart_id")
	private Long cartId;
	
	@Column(name="username")
	private String username;
	
	@Column(name="bill")
	private Long bill;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "shoppingCart")
	@JsonManagedReference
	private List<CartItem> cartItem=new ArrayList<>();
	
	
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
	
	
	
	
}
