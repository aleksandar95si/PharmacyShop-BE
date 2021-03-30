package fon.master.nst.shoppingcart.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.repository.CartItemRepository;
import fon.master.nst.shoppingcart.repository.ShoppingCartRepository;
import fon.master.nst.shoppingcart.service.ShoppingCartService;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private CurrentLoggedInUserService currentLoggedInUserService;
	
	public void addItem(Long productId) {
		
	// 1) Proveriti da li postoji korpa za datog Usera, ako ne postoji napraviti je
		ShoppingCart currShopCart;
		currShopCart=getShoppingCart();
		if(currShopCart==null) {
			currShopCart=new ShoppingCart(currentLoggedInUserService.getCurrentUser()); 		
			currShopCart.setBill(0L);
		}
	
	// 2) Pronadji proizvod
		HttpHeaders httpHeader=new HttpHeaders();
		httpHeader.add("Authorization", AccesTokenService.getAccesToken());
		HttpEntity<Product> productEntity=new HttpEntity<>(httpHeader);
		ResponseEntity<Product> responseEntity=restTemplate.exchange("http://PRODUCT-SERVICE/products/"+productId,
																	HttpMethod.GET, productEntity, Product.class);
		Product currProd=responseEntity.getBody();
		
	// 3) Napraviti novi CartItem, dodeliti mu ID proizvoda i ime i povezati sa Cart-om 
		CartItem cartItem=new CartItem(currShopCart);
		cartItem.setProductId(currProd.getProductId());
		cartItem.setProductName(currProd.getName());
		cartItem.setPrice(currProd.getPrice());
	
	// 4) Dodati item u listu Item-a korpe
		List<CartItem> lista=new ArrayList<>();
		lista.add(cartItem);
		currShopCart.setCartItem(lista);
		currShopCart.setBill(currShopCart.getBill()+cartItem.getPrice());

	// 5) Sacuvati ShoppingCart
		shoppingCartRepository.save(currShopCart);		
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCartRepository.findByUsername(currentLoggedInUserService.getCurrentUser());
	}
	
	public void removeCartItem(Long itemId) {
		CartItem item=cartItemRepository.findByItemId(itemId);
		ShoppingCart cart=shoppingCartRepository.findByCartItemItemId(item.getItemId());
		if(cart.getBill()-item.getPrice()<0) {
			cart.setBill(0L);
		} else {
			cart.setBill(cart.getBill()-item.getPrice());
		}
		cartItemRepository.deleteById(itemId);		
	}
	
	public void deleteCart(Long cartId) {
		shoppingCartRepository.deleteById(cartId);
	}
	
	public ShoppingCart getCartById(Long cartId) {
		return shoppingCartRepository.findByCartId(cartId);
	}

	public CartItem getItem(Long cartId) {
		return cartItemRepository.findByItemId(cartId);
	}
}
