package fon.master.nst.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fon.master.nst.shoppingcart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	//@Query
	//ShoppingCart findByShoppingCartUserId(Long userId);
	
	ShoppingCart findByCartId(Long cartId);
	
}
