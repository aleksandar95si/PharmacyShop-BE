package fon.master.nst.productservice.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import fon.master.nst.productservice.exception.ApiException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductCategory;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import fon.master.nst.productservice.service.impl.ProductServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductServiceImpl productServiceImpl;
    @MockBean
    private ProductGroupServiceImpl productGroupServiceImpl;

    @Test
    void testGetProduct() throws Exception {
        ProductCategory testProductCategory1 = new ProductCategory();
        testProductCategory1.setCategoryId(1L);
        testProductCategory1.setName("testGroupName1");

        ProductCategory testProductCategory2 = new ProductCategory();
        testProductCategory2.setCategoryId(2L);
        testProductCategory2.setName("testGroupName2");

        Product testProduct1 = new Product();
        testProduct1.setProductId(1L);
        testProduct1.setName("testProductName1");
        testProduct1.setPrice(100L);
        testProduct1.setProductImgPath("/test/test1.png");
        testProduct1.setProductCategory(testProductCategory1);

        Product testProduct2 = new Product();
        testProduct2.setProductId(2L);
        testProduct2.setName("testProductName2");
        testProduct2.setPrice(100L);
        testProduct2.setProductImgPath("/test/test2.png");
        testProduct2.setProductCategory(testProductCategory2);

        when(productServiceImpl.findAllProducts()).thenReturn(List.of(testProduct1, testProduct2));

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[0].productGroup.name", is("testGroupName1")))
                .andExpect(jsonPath("$[1].productId", is(2)))
                .andExpect(jsonPath("$[1].productGroup.name", is("testGroupName2")));


    }

    @Test
    void testGetProductsByGroupName() throws Exception {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setCategoryId(1L);
        testProductCategory.setName("testGroupName");

        Product testProduct1 = new Product();
        testProduct1.setProductId(1L);
        testProduct1.setName("testProductName1");
        testProduct1.setPrice(100L);
        testProduct1.setProductImgPath("/test/test1.png");
        testProduct1.setProductCategory(testProductCategory);

        Product testProduct2 = new Product();
        testProduct2.setProductId(2L);
        testProduct2.setName("testProductName2");
        testProduct2.setPrice(100L);
        testProduct2.setProductImgPath("/test/test2.png");
        testProduct2.setProductCategory(testProductCategory);

        when(productServiceImpl.getAllProductsByGroupName(testProductCategory.getName())).thenReturn(List.of(testProduct1, testProduct2));

        mockMvc.perform(get("/products/group/" + testProductCategory.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(1)))
                .andExpect(jsonPath("$[1].productId", is(2)));

    }

    @Test
    void testGetProductsByGroupNameNotFound() throws Exception {
        when(productServiceImpl.getAllProductsByGroupName("wrongGroupName")).thenThrow(new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name()));

        mockMvc.perform(get("/products/group/wrongGroupName")).andExpect(status().isNotFound());
    }

    @Test
    void testFindByProductId() throws Exception {
        Product testProduct = new Product();
        testProduct.setProductId(1L);

        when(productServiceImpl.findByProductId(testProduct.getProductId())).thenReturn(testProduct);

        mockMvc.perform(get("/products/" + testProduct.getProductId()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productId", is(1)));
    }
}
