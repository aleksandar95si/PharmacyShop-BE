package fon.master.nst.shoppingcart.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;

@SpringBootTest
class ShoppingCartRepositoryTest {

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;
	@Autowired
	private CartItemRepository cartItemRepository;
	
	private ShoppingCart testShoppingCart;
	
	@BeforeEach
	void beforeEach() {
		testShoppingCart=new ShoppingCart("testUser");
	}
	
	@AfterEach
	void afterEach() {
		cartItemRepository.deleteAll();
		shoppingCartRepository.deleteAll();
	}
	
	@Test
	void testFindByCartId() {
		shoppingCartRepository.save(testShoppingCart);
		
		ShoppingCart shoppingCartResult=shoppingCartRepository.findByCartId(testShoppingCart.getCartId());
		
		assertEquals(testShoppingCart, shoppingCartResult);
	}

	@Test
	void testFindByUsername() {
		shoppingCartRepository.save(testShoppingCart);
		
		ShoppingCart shoppingCartResult=shoppingCartRepository.findByUsername(testShoppingCart.getUsername());
		
		assertEquals(testShoppingCart, shoppingCartResult);
	}

	@Test
	void testFindByCartItemItemId() {
		CartItem testCartItem=new CartItem(testShoppingCart);
		testShoppingCart.getCartItem().add(testCartItem);
		
		shoppingCartRepository.save(testShoppingCart);
		
		ShoppingCart shoppingCartResult=shoppingCartRepository.findByCartItemItemId(testCartItem.getItemId());
	
		assertEquals(testShoppingCart, shoppingCartResult);
	}

}
