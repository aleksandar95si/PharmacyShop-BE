package fon.master.nst.productservice.service;

import java.util.List;

import fon.master.nst.productservice.model.Product;

public interface ProductService {

    List<Product> getAllProductsByGroupName(String name);

    List<Product> findAllProducts();

    Product findByProductId(Long productId);

    boolean updateProductsTotalAmount(List<Product> products);

    List<Product> findProductsById(List<Long> ids);

}
