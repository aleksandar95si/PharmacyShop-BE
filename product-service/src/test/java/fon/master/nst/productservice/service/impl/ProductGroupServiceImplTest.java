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

import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductGroupRepository;

@SpringBootTest
class ProductGroupServiceImplTest {

    @Autowired
    private ProductGroupServiceImpl productGroupServiceImpl;
    @MockBean
    private ProductGroupRepository productGroupRepository;

    @Test
    void testFindByName() {
        ProductGroup testProductGroup = new ProductGroup();
        testProductGroup.setGroupId(1L);
        testProductGroup.setName("testGroupName");

        when(productGroupRepository.findByName("testGroupName")).thenReturn(testProductGroup);

        ProductGroup productGroupResult = productGroupServiceImpl.findByName("testGroupName");

        assertEquals(testProductGroup, productGroupResult);
    }

    @Test
    void testGetAllGroups() {
        ProductGroup testProductGroup1 = new ProductGroup();
        testProductGroup1.setGroupId(1L);
        testProductGroup1.setName("testGroupName1");

        ProductGroup testProductGroup2 = new ProductGroup();
        testProductGroup2.setGroupId(2L);
        testProductGroup2.setName("testGroupName2");

        List<ProductGroup> testProductGroupList = new ArrayList<>();
        testProductGroupList.add(testProductGroup1);
        testProductGroupList.add(testProductGroup2);

        when(productGroupRepository.findAll()).thenReturn(testProductGroupList);

        List<ProductGroup> productGroupListResult = productGroupServiceImpl.getAllGroups();

        assertEquals(2, productGroupListResult.size());
        assertEquals(testProductGroupList.get(0), productGroupListResult.get(0));
        assertEquals(testProductGroupList.get(1), productGroupListResult.get(1));
    }

    @Test
    public void testAddProductGroup() {
        ProductGroup testProductGroup = new ProductGroup();
        testProductGroup.setGroupId(1L);
        testProductGroup.setName("testGroupName");

        productGroupServiceImpl.addProductGroup(testProductGroup);

        verify(productGroupRepository, times(1)).save(testProductGroup);
    }

}
