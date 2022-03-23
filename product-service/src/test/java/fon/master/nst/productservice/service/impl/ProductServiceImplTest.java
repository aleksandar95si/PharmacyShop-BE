package fon.master.nst.productservice.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductRepository;

@SpringBootTest
class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productServiceImpl;
    @MockBean
    private ProductRepository productRepository;


    @Test
    void testGetAllProductsByGroupName() throws ProductGroupException {
        ProductGroup testProductGroup = new ProductGroup();
        testProductGroup.setGroupId(1L);
        testProductGroup.setName("TestGroup");

        Product testProduct1 = new Product();
        testProduct1.setProductId(1L);
        testProduct1.setName("TestProduct1");
        testProduct1.setPrice(100L);
        testProduct1.setProductGroup(testProductGroup);

        Product testProduct2 = new Product();
        testProduct2.setProductId(2L);
        testProduct2.setName("TestProduct2");
        testProduct2.setPrice(200L);
        testProduct2.setProductGroup(testProductGroup);

        List<Product> testList = new ArrayList<>();
        testList.add(testProduct1);
        testList.add(testProduct2);

        when(productRepository.findByProductGroupName(testProductGroup.getName())).thenReturn(testList);

        List<Product> testResult = productServiceImpl.getAllProductsByGroupName(testProductGroup.getName());

        assertEquals(2, testResult.size());
        assertEquals(testList.get(0), testResult.get(0));
        assertEquals(testList.get(1), testResult.get(1));

    }

    @Test
    public void testGetAllProductsByGroupNameException1() {

        when(productRepository.findByProductGroupName("wrongGroupName")).thenReturn(null);

        assertThrows(ProductGroupException.class, () -> {
            productServiceImpl.getAllProductsByGroupName("wrongGroupName");
        });

    }

    @Test
    public void testGetAllProductsByGroupNameException2() {

        when(productRepository.findByProductGroupName("emptyGroupName")).thenReturn(Collections.emptyList());

        assertThrows(ProductGroupException.class, () -> {
            productServiceImpl.getAllProductsByGroupName("emptyGroupName");
        });

    }

    @Test
    public void testFindByProductId() {
        Product testProduct = new Product();
        testProduct.setProductId(1L);

        when(productRepository.findByProductId(testProduct.getProductId())).thenReturn(testProduct);

        Product productResult = productServiceImpl.findByProductId(testProduct.getProductId());

        assertEquals(testProduct, productResult);
    }

    @Test
    public void testAddProduct() {
        Product testProduct = new Product();
        testProduct.setProductId(1L);

        productServiceImpl.addProduct(testProduct);
        ;

        verify(productRepository, times(1)).save(testProduct);
    }

    @Test
    public void testDeleteById() {
        Product testProduct = new Product();
        testProduct.setProductId(1L);

        productServiceImpl.deleteById(testProduct.getProductId());

        verify(productRepository, times(1)).deleteById(testProduct.getProductId());
    }

    @Test
    public void testFindAllProducts() {
        Product testProduct1 = new Product();
        testProduct1.setProductId(1L);
        testProduct1.setName("TestProduct1");
        testProduct1.setPrice(100L);

        Product testProduct2 = new Product();
        testProduct2.setProductId(2L);
        testProduct2.setName("TestProduct2");
        testProduct2.setPrice(200L);

        when(productRepository.findAll()).thenReturn(List.of(testProduct1, testProduct2));

        List<Product> productListResult = productServiceImpl.findAllProducts();

        assertEquals(2, productListResult.size());
        assertEquals(testProduct1, productListResult.get(0));
        assertEquals(testProduct2, productListResult.get(1));
    }
}
