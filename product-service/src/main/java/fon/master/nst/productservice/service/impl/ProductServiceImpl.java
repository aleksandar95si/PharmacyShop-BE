package fon.master.nst.productservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import fon.master.nst.productservice.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.repository.ProductRepository;
import fon.master.nst.productservice.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    private final Logger logger = LoggerFactory.getLogger(ProductService.class);

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> getAllProductsByGroupName(String name) {

        List<Product> listOfProductsByGroupName = productRepository.findByProductGroupName(name);

        if (listOfProductsByGroupName == null || listOfProductsByGroupName.isEmpty()) {
            logger.error("Invalid group name!");
            throw new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
        }
        logger.info("List of products was showed");
        return listOfProductsByGroupName;
    }

    public Product findByProductId(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ApiException("There is no product with id " + productId, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name()));
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

}
