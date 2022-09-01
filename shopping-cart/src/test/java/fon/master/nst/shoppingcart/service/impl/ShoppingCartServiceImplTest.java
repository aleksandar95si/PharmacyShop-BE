package fon.master.nst.shoppingcart.service.impl;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.repository.CartItemRepository;
import fon.master.nst.shoppingcart.repository.ShoppingCartRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ShoppingCartServiceImplTest {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;
    @MockBean
    private ShoppingCartRepository shoppingCartRepository;
    @MockBean
    private CartItemRepository cartItemRepository;
    @MockBean
    private RestTemplate restTemplate;

    @Test
    void testRemoveCartItem1() {
        ShoppingCart testShoppingCart = new ShoppingCart();
        testShoppingCart.setCartId(1L);
        testShoppingCart.setBill(0L);

        CartItem testCartItem = new CartItem();
        testCartItem.setItemId(1L);
        testCartItem.setShoppingCart(testShoppingCart);
        testCartItem.setPrice(10L);

        when(cartItemRepository.findByItemId(testCartItem.getItemId())).thenReturn(testCartItem);
        when(shoppingCartRepository.findByCartItemItemId(testCartItem.getItemId())).thenReturn(testShoppingCart);

        shoppingCartServiceImpl.removeCartItem(testCartItem.getItemId());

        verify(cartItemRepository, times(1)).deleteById(testCartItem.getItemId());
    }

    @Test
    void testRemoveCartItem2() {
        ShoppingCart testShoppingCart = new ShoppingCart();
        testShoppingCart.setCartId(1L);
        testShoppingCart.setBill(100L);

        CartItem testCartItem = new CartItem();
        testCartItem.setItemId(1L);
        testCartItem.setShoppingCart(testShoppingCart);
        testCartItem.setPrice(10L);

        when(cartItemRepository.findByItemId(testCartItem.getItemId())).thenReturn(testCartItem);
        when(shoppingCartRepository.findByCartItemItemId(testCartItem.getItemId())).thenReturn(testShoppingCart);

        shoppingCartServiceImpl.removeCartItem(testCartItem.getItemId());

        verify(cartItemRepository, times(1)).deleteById(testCartItem.getItemId());
    }

    @Test
    void testDeleteCart() {
        ShoppingCart testShoppingCart = new ShoppingCart();
        testShoppingCart.setCartId(1L);

        shoppingCartServiceImpl.deleteCart(testShoppingCart.getCartId());
        verify(shoppingCartRepository, times(1)).deleteById(testShoppingCart.getCartId());
    }

    @Test
    void testGetCartById() {
        ShoppingCart testShoppingCart = new ShoppingCart();
        testShoppingCart.setCartId(1L);

        when(shoppingCartRepository.findById(testShoppingCart.getCartId())).thenReturn(Optional.of(testShoppingCart));

        ShoppingCart shoppingCartResult = shoppingCartServiceImpl.getCartById(testShoppingCart.getCartId());

        assertEquals(testShoppingCart, shoppingCartResult);
    }

    @Test
    void testGetItem() {
        CartItem testCartItem = new CartItem();
        testCartItem.setItemId(1L);

        when(cartItemRepository.findByItemId(testCartItem.getItemId())).thenReturn(testCartItem);

        CartItem cartItemResult = shoppingCartServiceImpl.getItem(testCartItem.getItemId());

        assertEquals(testCartItem, cartItemResult);
    }
}
