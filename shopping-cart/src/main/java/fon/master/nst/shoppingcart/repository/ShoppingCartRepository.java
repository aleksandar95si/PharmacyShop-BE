package fon.master.nst.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fon.master.nst.shoppingcart.model.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long>{

	ShoppingCart findByCartId(Long cartId);
	
	
	ShoppingCart findByUsername(String username);
	
	
	ShoppingCart findByCartItemItemId(Long itemId);
}
