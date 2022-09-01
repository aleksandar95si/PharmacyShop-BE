package fon.master.nst.shoppingcart.service.impl;

import feign.FeignException;
import fon.master.nst.shoppingcart.client.ProductClient;
import fon.master.nst.shoppingcart.dto.Product;
import fon.master.nst.shoppingcart.exception.ApiException;
import fon.master.nst.shoppingcart.model.CartItem;
import fon.master.nst.shoppingcart.model.ShoppingCart;
import fon.master.nst.shoppingcart.repository.CartItemRepository;
import fon.master.nst.shoppingcart.repository.ShoppingCartRepository;
import fon.master.nst.shoppingcart.service.ShoppingCartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductClient productClient;

    private final Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

    public void addItem(Long productId) {

        // 1) Proveriti da li postoji korpa za ulogovanog Usera, ako ne postoji napraviti je
        ShoppingCart currShopCart = getShoppingCart();
        if (currShopCart == null) {
            currShopCart = new ShoppingCart(AuthService.getCurrentUsersUsername());
            currShopCart.setBill(0L);
            logger.info("Shopping cart created for user: " + AuthService.getCurrentUsersUsername());
        }

        // 2) Pronadji proizvod
        Product currProd;
        try {
            currProd = productClient.getProduct(productId);
        } catch (FeignException feignException) {
            throw new ApiException("Error during fetching products data from products-api.", HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name());
        }

        // 3) Napraviti novi CartItem, dodeliti mu ID proizvoda i ime i povezati sa Cart-om
        CartItem cartItem = new CartItem(currShopCart);
        cartItem.setProductId(currProd.getProductId());
        cartItem.setProductName(currProd.getName());
        cartItem.setPrice(currProd.getPrice());

        // 4) Dodati item u listu Item-a korpe
        List<CartItem> listOfItems = currShopCart.getCartItem();
        listOfItems.add(cartItem);
        logger.info("Added item: {}", cartItem);
        currShopCart.setCartItem(listOfItems);
        currShopCart.setBill(currShopCart.getBill() + cartItem.getPrice());

        // 5) Sacuvati ShoppingCart
        shoppingCartRepository.save(currShopCart);
        logger.info("Shopping cart updated");
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCartRepository.findByUsername(AuthService.getCurrentUsersUsername());
    }

    public void removeCartItem(Long itemId) {

        CartItem item = cartItemRepository.findByItemId(itemId);

        ShoppingCart cart = shoppingCartRepository.findByCartItemItemId(item.getItemId());

        if (cart.getBill() - item.getPrice() < 0) {
            cart.setBill(0L);
        } else {
            cart.setBill(cart.getBill() - item.getPrice());
        }

        cartItemRepository.deleteById(itemId);

        logger.info("Cart item deleted");
    }

    public void deleteCart(Long cartId) {
        shoppingCartRepository.deleteById(cartId);
    }

    public ShoppingCart getCartById(Long cartId) {
        return shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new ApiException("", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name()));
    }

    public CartItem getItem(Long cartId) {
        return cartItemRepository.findByItemId(cartId);
    }

}
