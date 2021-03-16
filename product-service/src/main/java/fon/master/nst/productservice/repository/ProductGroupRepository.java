package fon.master.nst.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fon.master.nst.productservice.model.ProductGroup;

@Repository
public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {

	ProductGroup findByName(String name);

}
