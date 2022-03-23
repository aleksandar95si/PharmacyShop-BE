package fon.master.nst.orderservice.client;

import fon.master.nst.orderservice.dto.ShoppingCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "SHOPPING-CART")
public interface ShoppingCartClient {

    @GetMapping("http://SHOPPING-CART/cart/get")
    ShoppingCart getShoppingCart(@RequestHeader("Authorization") String accessToken);

}
