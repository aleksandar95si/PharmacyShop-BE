package fon.master.nst.productservice.controller;

import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups")
public class ProductGroupController {

    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;

    @GetMapping
    public ResponseEntity<List<ProductGroup>> getAllGroups() {
        return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.getAllGroups());
    }

    @GetMapping("/{name}")
    public ResponseEntity<ProductGroup> findGroupByName(@PathVariable("name") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productGroupServiceImpl.findByName(name));
    }

    @PostMapping
    public ResponseEntity addGroup(@RequestBody ProductGroup productGroup) {
        productGroupServiceImpl.addProductGroup(productGroup);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
