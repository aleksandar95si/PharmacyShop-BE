package fon.master.nst.productservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductGroupRepository;
import fon.master.nst.productservice.service.ProductGroupService;

@Service
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService {
	
	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	public void addProductGroup(ProductGroup productGroup) {
		productGroupRepository.save(productGroup);
	}
	
	public ProductGroup findByName(String name) throws ProductGroupException {
		
		ProductGroup productGroup=productGroupRepository.findByName(name);
		
		if(productGroup==null) {
			throw new ProductGroupException("Invalid group name");
		}
		
		return productGroup;
	}
	
	public List<ProductGroup> getAllGroups() throws ProductGroupException {
		
		List<ProductGroup> listOfAllGroups=productGroupRepository.findAll();
		
		if(listOfAllGroups.isEmpty()) {
			throw new ProductGroupException("Group not found");
		}
		
		return listOfAllGroups;
	}
}
