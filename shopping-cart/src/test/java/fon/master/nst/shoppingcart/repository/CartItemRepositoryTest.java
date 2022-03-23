package fon.master.nst.shoppingcart.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;

@SpringBootTest
class CartItemRepositoryTest {

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    void testFindByItemId() {
        ShoppingCart testShoppingCart = new ShoppingCart("testUser");

        CartItem testCartItem = new CartItem(testShoppingCart);
        testShoppingCart.getCartItem().add(testCartItem);

        shoppingCartRepository.save(testShoppingCart);

        CartItem cartItemResult = cartItemRepository.findByItemId(testCartItem.getItemId());

        assertEquals(testCartItem, cartItemResult);
    }

}
