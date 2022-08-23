package fon.master.nst.productservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fon.master.nst.productservice.model.ProductCategory;
import fon.master.nst.productservice.repository.ProductCategoryRepository;

@SpringBootTest
class ProductCategoryServiceImplTest {

    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void testFindByName() {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setCategoryId(1L);
        testProductCategory.setName("testGroupName");

        when(productCategoryRepository.findByName("testGroupName")).thenReturn(testProductCategory);

        ProductCategory productCategoryResult = productGroupServiceImpl.findByName("testGroupName");

        assertEquals(testProductCategory, productCategoryResult);
    }

    @Test
    void testGetAllGroups() {
        ProductCategory testProductCategory1 = new ProductCategory();
        testProductCategory1.setCategoryId(1L);
        testProductCategory1.setName("testGroupName1");

        ProductCategory testProductCategory2 = new ProductCategory();
        testProductCategory2.setCategoryId(2L);
        testProductCategory2.setName("testGroupName2");

        List<ProductCategory> testProductCategoryList = new ArrayList<>();
        testProductCategoryList.add(testProductCategory1);
        testProductCategoryList.add(testProductCategory2);

        when(productCategoryRepository.findAll()).thenReturn(testProductCategoryList);

        List<ProductCategory> productCategoryListResult = productGroupServiceImpl.getAllGroups();

        assertEquals(2, productCategoryListResult.size());
        assertEquals(testProductCategoryList.get(0), productCategoryListResult.get(0));
        assertEquals(testProductCategoryList.get(1), productCategoryListResult.get(1));
    }

    @Test
    public void testAddProductGroup() {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setCategoryId(1L);
        testProductCategory.setName("testGroupName");

        productGroupServiceImpl.addProductGroup(testProductCategory);

        verify(productCategoryRepository, times(1)).save(testProductCategory);
    }

}
