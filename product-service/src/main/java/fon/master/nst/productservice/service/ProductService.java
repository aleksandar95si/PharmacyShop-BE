package fon.master.nst.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}
	
	public List<Product> getAllProductsByGroupName(String name) {
		return productRepository.findByProductGroupName(name);
	}
}
