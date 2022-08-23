package fon.master.nst.productservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.productservice.model.ProductCategory;

@SpringBootTest
class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void testFindByName() {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setCategoryId(1L);
        testProductCategory.setName("testGroupName");
        testProductCategory.setCategoryImgPath("");
        productCategoryRepository.save(testProductCategory);
        ProductCategory productCategoryResult = productCategoryRepository.findByName("testGroupName");
        assertEquals(testProductCategory, productCategoryResult);
    }
}
