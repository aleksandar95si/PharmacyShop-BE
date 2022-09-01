package fon.master.nst.productservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import fon.master.nst.productservice.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;
    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping
    public ResponseEntity<List<Product>> getProducts(@RequestParam(required = false) List<Long> ids) {

        if (ids == null || ids.isEmpty()) {
            return ResponseEntity.ok(productServiceImpl.findAllProducts());
        } else {
            List<Product> products = productServiceImpl.findProductsById(ids);
            return products == null || products.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(products);        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findByProductId(@PathVariable("id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productServiceImpl.findByProductId(productId));
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getProductsByGroupName(@PathVariable("name") String name) {

        return ResponseEntity.status(HttpStatus.OK).body(productServiceImpl.getAllProductsByGroupName(name));
    }

    @PostMapping
    public ResponseEntity<?> updateProductsTotalAmount(@RequestBody List<Product> products) {

        boolean isUpdated = productServiceImpl.updateProductsTotalAmount(products);

        return isUpdated ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}
