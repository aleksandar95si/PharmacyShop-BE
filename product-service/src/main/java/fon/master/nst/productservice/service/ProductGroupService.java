package fon.master.nst.productservice.service;

import java.util.List;

import fon.master.nst.productservice.exceptions.ProductGroupException;
import fon.master.nst.productservice.model.ProductGroup;

public interface ProductGroupService {

    void addProductGroup(ProductGroup productGroup);

    ProductGroup findByName(String name) throws ProductGroupException;

    List<ProductGroup> getAllGroups() throws ProductGroupException;

}
