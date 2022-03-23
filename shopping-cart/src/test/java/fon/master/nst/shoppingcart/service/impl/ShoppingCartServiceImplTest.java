package fon.master.nst.shoppingcart.service.impl;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.repository.CartItemRepository;
import fon.master.nst.shoppingcart.repository.ShoppingCartRepository;

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
    @MockBean
    private CurrentLoggedInUserService currentLoggedInUserService;

    @Test
    void testAddItem() {
        Product testProduct = new Product();
        testProduct.setProductId(1L);
        testProduct.setPrice(10L);

        ShoppingCart testShoppingCart = new ShoppingCart("testUser");
        testShoppingCart.setBill(20L);

        ResponseEntity<Product> responseProduct = new ResponseEntity<Product>(testProduct, HttpStatus.OK);

        when(shoppingCartServiceImpl.getShoppingCart()).thenReturn(testShoppingCart);
        when(currentLoggedInUserService.getCurrentUser()).thenReturn("testUser");
        mockStatic(AccessTokenService.class).when(AccessTokenService::getAccessToken).thenReturn("testToken");
        when(restTemplate.exchange(Mockito.eq("http://PRODUCT-SERVICE/products/" + testProduct.getProductId()),
                Mockito.eq(HttpMethod.GET), Mockito.<HttpEntity<Product>>any(), Mockito.<Class<Product>>any())).thenReturn(responseProduct);

        shoppingCartServiceImpl.addItem(testProduct.getProductId());

        verify(shoppingCartRepository, times(1)).save(testShoppingCart);
    }

    //@Test
    void testAddItemShoppingCartIsNull() {
        Product testProduct = new Product();
        testProduct.setProductId(1L);
        testProduct.setPrice(10L);

        ShoppingCart testShoppingCart = null;

        ResponseEntity<Product> responseProduct = new ResponseEntity<Product>(testProduct, HttpStatus.OK);

        when(shoppingCartServiceImpl.getShoppingCart()).thenReturn(testShoppingCart);
        when(currentLoggedInUserService.getCurrentUser()).thenReturn("testUser1");
        //mockStatic(AccesTokenService.class).when(AccesTokenService::getAccesToken).thenReturn("testToken");
        when(restTemplate.exchange(Mockito.eq("http://PRODUCT-SERVICE/products/" + testProduct.getProductId()),
                Mockito.eq(HttpMethod.GET), Mockito.<HttpEntity<Product>>any(), Mockito.<Class<Product>>any())).thenReturn(responseProduct);

        shoppingCartServiceImpl.addItem(testProduct.getProductId());

        //testShoppingCart=new ShoppingCart("testUser");
        //testShoppingCart.setBill(0L);
        verify(shoppingCartRepository, times(1)).save(testShoppingCart);
    }

    @Test
    void testGetShoppingCart() {

        when(currentLoggedInUserService.getCurrentUser()).thenReturn("testUser");

        shoppingCartServiceImpl.getShoppingCart();
    }

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

        when(shoppingCartRepository.findByCartId(testShoppingCart.getCartId())).thenReturn(testShoppingCart);

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
