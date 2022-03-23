package fon.master.nst.productservice.service;

import java.util.List;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.Product;

public interface ProductService {

    List<Product> getAllProductsByGroupName(String name) throws ProductGroupException;

    List<Product> findAllProducts();

    Product findByProductId(Long productId);

    void addProduct(Product product);

    void deleteById(Long id);

}
