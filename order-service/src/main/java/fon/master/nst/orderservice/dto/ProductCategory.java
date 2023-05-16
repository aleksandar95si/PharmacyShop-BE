package fon.master.nst.orderservice.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;
    private String name;
    @Column(name = "category_img_path")
    private String categoryImgPath;

}