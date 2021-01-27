package fon.master.nst.productservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fon.master.nst.productservice.model.Product;
import fon.master.nst.productservice.service.ProductService;

@RestController
@RequestMapping("/products")
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
	
	
}
