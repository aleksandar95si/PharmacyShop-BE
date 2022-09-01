package fon.master.nst.productservice.service;

import java.util.List;

import fon.master.nst.productservice.model.ProductCategory;

public interface ProductGroupService {

    void addProductGroup(ProductCategory productCategory);

    ProductCategory findByName(String name);

    List<ProductCategory> getAllGroups();

}
