package fon.master.nst.shoppingcart.controller;

import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.service.impl.ShoppingCartServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartServiceImpl shoppingCartServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);

    @GetMapping
    public ResponseEntity<ShoppingCart> getShoppingCart() {
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartServiceImpl.getShoppingCart());
    }

    @PostMapping("/{id}")
    public ResponseEntity addItem(@PathVariable("id") Long productId) {
        logger.info("User clicked on addItem method");
        shoppingCartServiceImpl.addItem(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<CartItem> getItem(@PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartServiceImpl.getItem(id));
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long itemId) {
        logger.info("User clicked on deleteItem method");
        shoppingCartServiceImpl.removeCartItem(itemId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCart(@PathVariable("id") Long cartId) {
        shoppingCartServiceImpl.deleteCart(cartId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
