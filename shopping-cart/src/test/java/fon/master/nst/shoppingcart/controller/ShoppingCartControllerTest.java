package fon.master.nst.shoppingcart.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.service.impl.ShoppingCartServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ShoppingCartControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ShoppingCartServiceImpl shoppingCartServiceImpl;
	
	@Test
	void testAddItem() throws Exception {
		Long productId=new Random().nextLong();
		mockMvc.perform(post("/cart/addItem/"+productId)).andExpect(status().isNoContent());
		verify(shoppingCartServiceImpl, times(1)).addItem(productId);
	}

	@Test
	void testGetShoppingCart() throws Exception {
		ShoppingCart testShoppingCart=new ShoppingCart("testUser");
		
		when(shoppingCartServiceImpl.getShoppingCart()).thenReturn(testShoppingCart);
		
		mockMvc.perform(get("/cart/get"))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.cartId", is(testShoppingCart.getCartId())))
				.andExpect(jsonPath("$.username", is(testShoppingCart.getUsername())));
				
						
	}

	@Test
	void testGetItem() throws Exception {
		CartItem testCartItem=new CartItem();
		testCartItem.setItemId(new Random().nextLong());
	
		when(shoppingCartServiceImpl.getItem(testCartItem.getItemId())).thenReturn(testCartItem);
		
		mockMvc.perform(get("/cart/item/get/"+testCartItem.getItemId()))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.itemId", is(testCartItem.getItemId())));
	}

	@Test
	void testDeleteItem() throws Exception {
		Long itemId=new Random().nextLong();
		mockMvc.perform(delete("/cart/item/"+itemId)).andExpect(status().isNoContent());
		verify(shoppingCartServiceImpl, times(1)).removeCartItem(itemId);
	}

	@Test
	void testDeleteCart() throws Exception {
		Long cartId=new Random().nextLong();
		mockMvc.perform(delete("/cart/"+cartId)).andExpect(status().isNoContent());
		verify(shoppingCartServiceImpl, times(1)).deleteCart(cartId);
	}

}
