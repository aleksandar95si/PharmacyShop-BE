package fon.master.nst.productservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "product_group")
public class ProductGroup implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="group_id")
	private Long groupId;
	private String name;
	@Column(name="group_img_path")
	private String groupImgPath;
	
	public ProductGroup() {
		
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupImgPath() {
		return groupImgPath;
	}

	public void setGroupImgPath(String groupImgPath) {
		this.groupImgPath = groupImgPath;
	}
	
	
}
