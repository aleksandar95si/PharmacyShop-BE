package fon.master.nst.orderservice.client;

import fon.master.nst.orderservice.config.FeignAuthConfiguration;
import fon.master.nst.orderservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignAuthConfiguration.class)
public interface ProductClient {

    @GetMapping("/products-api/products")
    List<Product> getProductsById(@RequestParam List<Long> ids);

    @PostMapping("/products-api/products")
    void updateProductsTotalAmount(@RequestBody List<Product> orderedProducts);
}
