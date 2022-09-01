package fon.master.nst.shoppingcart.client;

import fon.master.nst.shoppingcart.config.FeignAuthConfiguration;
import fon.master.nst.shoppingcart.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignAuthConfiguration.class)
public interface ProductClient {

    @GetMapping("/products-api/products/{productId}")
    Product getProduct(@PathVariable Long productId);

}
