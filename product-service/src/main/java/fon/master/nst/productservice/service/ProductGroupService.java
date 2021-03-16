package fon.master.nst.productservice.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductGroupRepository;

@Service
@Transactional
public class ProductGroupService {
	
	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	public void addProductGroup(ProductGroup productGroup) {
		productGroupRepository.save(productGroup);
	}
	
	public ProductGroup findByName(String name) {
		return productGroupRepository.findByName(name);
	}
	
}
