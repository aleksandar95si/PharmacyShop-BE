package fon.master.nst.shoppingcart.client;

import fon.master.nst.shoppingcart.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{productId}")
    Product getProduct(@RequestHeader("Authorization") String accessToken, @PathVariable Long productId);

}
