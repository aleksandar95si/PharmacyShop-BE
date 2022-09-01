package fon.master.nst.productservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductCategory;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    private ProductCategory testProductCategory;
    private Product testProduct1;
    private Product testProduct2;


    @BeforeEach
    void beforeEach() {
        testProductCategory = new ProductCategory();
        testProductCategory.setName("testGroupName");

        productCategoryRepository.save(testProductCategory);

        testProduct1 = new Product();
        testProduct1.setName("testProductName1");
        testProduct1.setPrice(100L);
        testProduct1.setProductCategory(testProductCategory);

        testProduct2 = new Product();
        testProduct2.setName("testProductName2");
        testProduct2.setPrice(200L);
        testProduct2.setProductCategory(testProductCategory);

        productRepository.save(testProduct1);
        productRepository.save(testProduct2);
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
        productCategoryRepository.deleteAll();
    }

    @Test
    void testFindByProductGroupName() {

        List<Product> productListResult = productRepository.findByProductCategoryName(testProductCategory.getName());

        assertEquals(2, productListResult.size());
        assertEquals(testProduct1, productListResult.get(0));
        assertEquals(testProduct2, productListResult.get(1));
    }
}
