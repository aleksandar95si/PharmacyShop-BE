package fon.master.nst.shoppingcart.VO;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;


public class Product implements Serializable {
	
	
	private Long productId;
	private String name;
	private int price;
	
	private ProductGroup productGroup;
	
	public Product() {
	
	}
	
	public Long getProductId() {
		return productId;
	}
	public void setProductid(Long productId) {
		this.productId = productId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public ProductGroup getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(ProductGroup productGroup) {
 		this.productGroup = productGroup;
    }

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", name=" + name + ", price=" + price + ", productGroup="
				+ productGroup + "]";
	}
	
	
}