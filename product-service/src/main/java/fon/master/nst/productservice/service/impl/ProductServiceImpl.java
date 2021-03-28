package fon.master.nst.productservice.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductGroupRepository;
import fon.master.nst.productservice.repository.ProductRepository;

@Service
@Transactional
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductGroupRepository productGroupRepository;
	
	
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getAllProductsByGroupName(String name) {
		return productRepository.findByProductGroupName(name);
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

	public List<ProductGroup> getAllGroups() {
		return productGroupRepository.findAll();
	}
}
