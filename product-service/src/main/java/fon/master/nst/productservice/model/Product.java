package fon.master.nst.productservice.model;

import lombok.Data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private Long price;
    @Column(name = "available_amount")
    private Long availableAmount;
    @Column(name = "reserved_amount")
    private Long reservedAmount;
    @Column(name = "product_img_path")
    private String productImgPath;
    @OneToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

}