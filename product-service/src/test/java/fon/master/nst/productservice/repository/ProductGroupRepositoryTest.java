package fon.master.nst.productservice.repository;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import fon.master.nst.productservice.model.ProductGroup;

@SpringBootTest
class ProductGroupRepositoryTest {

	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	@Test
	void testFindByName() {
		ProductGroup testProductGroup=new ProductGroup();
		testProductGroup.setGroupId(1L);
		testProductGroup.setName("testGroupName");
		testProductGroup.setGroupImgPath("");
		productGroupRepository.save(testProductGroup);
		ProductGroup productGroupResult=productGroupRepository.findByName("testGroupName");
		assertEquals(testProductGroup, productGroupResult);
	}
}
