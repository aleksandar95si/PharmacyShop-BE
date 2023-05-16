package fon.master.nst.orderservice.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;
    private String name;
    private Long price;
    @Column(name = "total_amount")
    private Long totalAmount;
    @Column(name = "product_img_path")
    private String productImgPath;
    @OneToOne
    @JoinColumn(name = "category_id")
    private ProductCategory productCategory;

}