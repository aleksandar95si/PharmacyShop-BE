package fon.master.nst.orchestrationservice.client;

import fon.master.nst.orchestrationservice.config.FeignAuthConfiguration;
import fon.master.nst.orchestrationservice.dto.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignAuthConfiguration.class)
public interface ProductClient {

    @GetMapping("/products-api/products")
    List<Product> getProductsById(@RequestParam List<Long> ids);

    @PostMapping("/products-api/products")
    void updateProductsTotalAmount(@RequestBody List<Product> orderedProducts);
}
