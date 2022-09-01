package fon.master.nst.productservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import fon.master.nst.productservice.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.model.ProductCategory;
import fon.master.nst.productservice.repository.ProductCategoryRepository;
import fon.master.nst.productservice.service.ProductGroupService;

@Service
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    public void addProductGroup(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    public ProductCategory findByName(String name) {

        ProductCategory productCategory = productCategoryRepository.findByName(name);

        if (productCategory == null) {
            throw new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
        }

        return productCategory;
    }

    public List<ProductCategory> getAllGroups() {

        return productCategoryRepository.findAll();
    }
}
