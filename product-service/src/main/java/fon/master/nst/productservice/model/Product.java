package fon.master.nst.productservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="product")
public class Product implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Long productId;
	private String name;
	private int price;
	@OneToOne
	@JoinColumn(name="group_id")
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
	
}