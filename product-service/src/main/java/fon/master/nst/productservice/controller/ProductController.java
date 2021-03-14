package fon.master.nst.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.service.ProductService;

@RestController
@RequestMapping("/products")
@EnableOAuth2Sso
public class ProductController {

	@Autowired
	private ProductService productService;
		
	@GetMapping("/all")
	public List<Product> getProduct() {
		return productService.findAllProducts();
	}
	
	@GetMapping("/group/{name}")
	public List<Product> getProductsByName(@PathVariable("name") String name) {
		return productService.getAllProductsByGroupName(name);
	}
	
	@GetMapping("/{id}")
	public Product findByProductId(@PathVariable("id") Long productId) {
		return productService.findByProductId(productId);	
	}
	
	@PostMapping(value="/add",  consumes = "application/json")
	public void addProduct(@RequestBody Product product) {
		productService.addProduct(product);
	}
}
