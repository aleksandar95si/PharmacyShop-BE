package fon.master.nst.productservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import fon.master.nst.productservice.service.impl.ProductServiceImpl;

@RestController
@RequestMapping("/products")
@EnableOAuth2Sso
public class ProductController {

    @Autowired
    private ProductServiceImpl productServiceImpl;
    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @GetMapping("/all")
    public List<Product> getProduct() {
        return productServiceImpl.findAllProducts();
    }

    @GetMapping("/group/{name}")
    public ResponseEntity<List<Product>> getProductsByGroupName(@PathVariable("name") String name) {
        logger.info("Clicked on group name: " + name);
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productServiceImpl.getAllProductsByGroupName(name));
        } catch (ProductGroupException e) {
            // TODO Auto-generated catch block
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }

    @GetMapping("/{id}")
    public Product findByProductId(@PathVariable("id") Long productId) {
        return productServiceImpl.findByProductId(productId);
    }

    @GetMapping("/group/all")
    public ResponseEntity<List<ProductGroup>> getAllGroups() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.getAllGroups());
        } catch (ProductGroupException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/add", consumes = "application/json")
    public ResponseEntity addProduct(@RequestBody Product product) {
        productServiceImpl.addProduct(product);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/group/get/{name}")
    public ResponseEntity<ProductGroup> findGroupByName(@PathVariable("name") String name) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.findByName(name));
        } catch (ProductGroupException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/group/add")
    public ResponseEntity addGroup(@RequestBody ProductGroup productGroup) {
        productGroupServiceImpl.addProductGroup(productGroup);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") Long id) {
        productServiceImpl.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
