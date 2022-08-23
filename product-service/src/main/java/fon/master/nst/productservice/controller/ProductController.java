package fon.master.nst.productservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Product> getProduct() {
        return productServiceImpl.findAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findByProductId(@PathVariable("id") Long productId) {
        return ResponseEntity.status(HttpStatus.OK).body(productServiceImpl.findByProductId(productId));
    }

    @GetMapping("/category/{name}")
    public ResponseEntity<List<Product>> getProductsByGroupName(@PathVariable("name") String name) {

        logger.info("Clicked on group name: " + name);

        return ResponseEntity.status(HttpStatus.OK).body(productServiceImpl.getAllProductsByGroupName(name));

    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        productServiceImpl.addProduct(product);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productServiceImpl.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
