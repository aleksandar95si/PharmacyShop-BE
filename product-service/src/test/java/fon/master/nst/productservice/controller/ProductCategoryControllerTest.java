package fon.master.nst.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fon.master.nst.productservice.exception.ApiException;
import fon.master.nst.productservice.model.ProductCategory;
import fon.master.nst.productservice.service.impl.ProductGroupServiceImpl;
import fon.master.nst.productservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class ProductCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ProductServiceImpl productServiceImpl;
    @MockBean
    private ProductGroupServiceImpl productGroupServiceImpl;

    @Test
    void testGetAllGroups() throws Exception {
        ProductCategory testProductCategory1 = new ProductCategory();
        testProductCategory1.setCategoryId(1L);
        testProductCategory1.setName("testGroupName1");

        ProductCategory testProductCategory2 = new ProductCategory();
        testProductCategory2.setCategoryId(2L);
        testProductCategory2.setName("testGroupName2");

        when(productGroupServiceImpl.getAllGroups()).thenReturn(List.of(testProductCategory1, testProductCategory2));

        mockMvc.perform(get("/groups"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].groupId", is(1)))
                .andExpect(jsonPath("$[0].name", is("testGroupName1")))
                .andExpect(jsonPath("$[1].groupId", is(2)))
                .andExpect(jsonPath("$[1].name", is("testGroupName2")));
    }

    @Test
    void testFindGroupByName() throws Exception {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setCategoryId(1L);
        testProductCategory.setName("testGroupName");

        when(productGroupServiceImpl.findByName(testProductCategory.getName())).thenReturn(testProductCategory);

        mockMvc.perform(get("/groups/" + testProductCategory.getName()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.groupId", is(1)))
                .andExpect(jsonPath("$.name", is("testGroupName")));
    }

    @Test
    void testFindGroupByNameNotFound() throws Exception {

        when(productGroupServiceImpl.findByName("wrongGroupName")).thenThrow(new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name()));

        mockMvc.perform(get("/groups/wrongGroupName")).andExpect(status().isNotFound());
    }

    @Test
    void testAddGroup() throws JsonProcessingException, Exception {
        ProductCategory testProductCategory = new ProductCategory();
        testProductCategory.setName("testGroupName");

        mockMvc.perform(post("/groups").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProductCategory)))
                .andExpect(status().isNoContent());
        verify(productGroupServiceImpl, times(1)).addProductGroup(testProductCategory);
    }

}
