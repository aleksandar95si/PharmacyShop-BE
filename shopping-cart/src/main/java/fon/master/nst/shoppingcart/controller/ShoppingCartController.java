package fon.master.nst.shoppingcart.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.service.ShoppingCartService;

@RestController
@RequestMapping("/cart")
@EnableOAuth2Sso
public class ShoppingCartController {

	@Autowired
	private ShoppingCartService shoppingCartService;
	
	
	@PostMapping("/addItem/{id}")
	public void addItem(@PathVariable("id") Long productId) {
		shoppingCartService.addItem(productId);
	}
	
	@GetMapping("/get")
	public ShoppingCart getShoppingCart() {
		return shoppingCartService.getShoppingCart();
	}
	
	@GetMapping("/item/get/{id}")
	public CartItem getItem(@PathVariable("id") Long id) {
		System.out.println(shoppingCartService.getItem(id));
		return shoppingCartService.getItem(id);
	}
	
	@DeleteMapping("/item/{id}")
	public void deleteItem (@PathVariable("id") Long itemId) {
		shoppingCartService.removeCartItem(itemId);
	}
	
	@DeleteMapping("/{id}")
	public void deleteCart (@PathVariable("id") Long cartId) {
		shoppingCartService.deleteCart(cartId);
	}

	// obrisati kasnije
	@GetMapping("/product/{id}") 
	public Product getProduct(@PathVariable("id") Long productId) {
		return shoppingCartService.getProductFromShopCart(productId);
	}
}
