package fon.master.nst.productservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductGroup;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductGroupRepository productGroupRepository;

    private ProductGroup testProductGroup;
    private Product testProduct1;
    private Product testProduct2;


    @BeforeEach
    void beforeEach() {
        testProductGroup = new ProductGroup();
        testProductGroup.setName("testGroupName");

        productGroupRepository.save(testProductGroup);

        testProduct1 = new Product();
        testProduct1.setName("testProductName1");
        testProduct1.setPrice(100L);
        testProduct1.setProductGroup(testProductGroup);

        testProduct2 = new Product();
        testProduct2.setName("testProductName2");
        testProduct2.setPrice(200L);
        testProduct2.setProductGroup(testProductGroup);

        productRepository.save(testProduct1);
        productRepository.save(testProduct2);
    }

    @AfterEach
    void afterEach() {
        productRepository.deleteAll();
        productGroupRepository.deleteAll();
    }

    @Test
    void testFindByProductGroupName() {

        List<Product> productListResult = productRepository.findByProductGroupName(testProductGroup.getName());

        assertEquals(2, productListResult.size());
        assertEquals(testProduct1, productListResult.get(0));
        assertEquals(testProduct2, productListResult.get(1));
    }
}
