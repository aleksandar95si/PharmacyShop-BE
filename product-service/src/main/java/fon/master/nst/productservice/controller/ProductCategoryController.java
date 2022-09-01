package fon.master.nst.productservice.controller;

import fon.master.nst.productservice.model.ProductCategory;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class ProductCategoryController {

    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllGroups() {
        return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.getAllGroups());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductCategory> findGroupByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.findByName(name));
    }

    @PostMapping
    public ResponseEntity addGroup(@RequestBody ProductCategory productCategory) {
        productGroupServiceImpl.addProductGroup(productCategory);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
