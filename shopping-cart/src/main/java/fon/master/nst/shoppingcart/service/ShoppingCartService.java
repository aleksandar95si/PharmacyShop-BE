package fon.master.nst.shoppingcart.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import fon.master.nst.shoppingcart.VO.Product;
import fon.master.nst.shoppingcart.VO.User;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.repository.CartItemRepository;
import fon.master.nst.shoppingcart.repository.ShoppingCartRepository;

@Service
@Transactional
public class ShoppingCartService {
	
	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private RestTemplate restTemplate;
	
	
	public void addItem(Product product) {
		
		// 1) Pronadji trenutno ulogovanog USER-a
		//User currUser=restTemplate.getForObject("http://USER-SERVICE//user/"+user.getUserId(), User.class);
		
		// 2) Proveriti da li postoji korpa za datog Usera, ako ne postoji napraviti je
		ShoppingCart currShopCart;
		/*try {
			//currShopCart=shoppingCartRepository.findByShoppingCartUser(currUser.getUserId());
			currShopCart=shoppingCartRepository.findByUser(1L); //hardkodovano
		
		} catch(Exception e) {
			//currShopCart=shoppingCartRepository.save(new ShoppingCart(currUser.getUserId()));
			currShopCart=shoppingCartRepository.save(new ShoppingCart(1L)); //hardkodovano
		}*/
		
		// 3) Pronadji ID proizvoda
		Product currProd=restTemplate.getForObject("http://localhost:8081/products/"+product.getProductId(), Product.class);
		
		// 4) Hardkodovan je User
		currShopCart=new ShoppingCart(5L); //hardkodovano
		//currShopCart.setUser(1L);
		//currShopCart=shoppingCartRepository.findByUser(1L);
		
		// 5) Napraviti novi CartItem, dodeliti mi ID proizvoda i povezati sa Cart-om 
		CartItem cartItem=new CartItem(currShopCart);
		cartItem.setProductId(currProd.getProductId());
		
		// 6) Dodati item u listu Item-a korpe
		List<CartItem> lista=new ArrayList<>();
		lista.add(cartItem);
		currShopCart.setCartItem(lista);
		
		// 7) Sacuvati Cart i Item
		shoppingCartRepository.save(currShopCart);	
		cartItemRepository.save(cartItem);
		
	}

	public ShoppingCart getShoppingCart(Long userId) {
		return shoppingCartRepository.findByUser(userId);
	}
	
	public void removeCartItem(Long itemId) {
		cartItemRepository.deleteById(itemId);
	}
	
	public void deleteCart(Long cartId) {
		shoppingCartRepository.deleteById(cartId);
	}
	
	
}
