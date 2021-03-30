package fon.master.nst.productservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.repository.ProductRepository;
import fon.master.nst.productservice.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getAllProductsByGroupName(String name) throws ProductGroupException {
		
		List<Product> listOfProductsByGroupName=productRepository.findByProductGroupName(name);
		
		if(listOfProductsByGroupName.isEmpty()) {
			throw new ProductGroupException("Invalid group name!");
		}
		
		return listOfProductsByGroupName;
	}

	public Product findByProductId(Long productId) {
		return productRepository.findByProductId(productId);
	}
	
	public void addProduct(Product product) {	
		productRepository.save(product);
	}

	public void deleteById(Long id) {
		productRepository.deleteById(id);	
	}

}
