package fon.master.nst.productservice.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import fon.master.nst.productservice.exception.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import fon.master.nst.productservice.model.ProductGroup;
import fon.master.nst.productservice.repository.ProductGroupRepository;
import fon.master.nst.productservice.service.ProductGroupService;

@Service
@Transactional
public class ProductGroupServiceImpl implements ProductGroupService {

    @Autowired
    private ProductGroupRepository productGroupRepository;

    public void addProductGroup(ProductGroup productGroup) {
        productGroupRepository.save(productGroup);
    }

    public ProductGroup findByName(String name) {

        ProductGroup productGroup = productGroupRepository.findByName(name);

        if (productGroup == null) {
            throw new ApiException("Invalid group name!", HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name());
        }

        return productGroup;
    }

    public List<ProductGroup> getAllGroups() {

        return productGroupRepository.findAll();
    }
}
