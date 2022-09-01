package fon.master.nst.shoppingcart.dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_category")
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    private String name;
    @Column(name = "category_img_path")
    private String categoryImgPath;

    public ProductCategory() {

    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImgPath() {
        return categoryImgPath;
    }

    public void setCategoryImgPath(String categoryImgPath) {
        this.categoryImgPath = categoryImgPath;
    }

    @Override
    public boolean equals(Object obj) {
        ProductCategory productCategory = (ProductCategory) obj;
        if (categoryId == productCategory.getCategoryId()) {
            return true;
        }
        return false;
    }

}