package fon.master.nst.productservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fon.master.nst.productservice.exception.ApiException;
import fon.master.nst.productservice.model.ProductGroup;
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
public class ProductGroupControllerTest {

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
        ProductGroup testProductGroup1 = new ProductGroup();
        testProductGroup1.setGroupId(1L);
        testProductGroup1.setName("testGroupName1");

        ProductGroup testProductGroup2 = new ProductGroup();
        testProductGroup2.setGroupId(2L);
        testProductGroup2.setName("testGroupName2");

        when(productGroupServiceImpl.getAllGroups()).thenReturn(List.of(testProductGroup1, testProductGroup2));

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
        ProductGroup testProductGroup = new ProductGroup();
        testProductGroup.setGroupId(1L);
        testProductGroup.setName("testGroupName");

        when(productGroupServiceImpl.findByName(testProductGroup.getName())).thenReturn(testProductGroup);

        mockMvc.perform(get("/groups/" + testProductGroup.getName()))
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
        ProductGroup testProductGroup = new ProductGroup();
        testProductGroup.setName("testGroupName");

        mockMvc.perform(post("/groups").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testProductGroup)))
                .andExpect(status().isNoContent());
        verify(productGroupServiceImpl, times(1)).addProductGroup(testProductGroup);
    }

}
