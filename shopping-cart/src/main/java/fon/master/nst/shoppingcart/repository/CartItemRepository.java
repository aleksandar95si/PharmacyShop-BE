package fon.master.nst.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fon.master.nst.shoppingcart.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

	
	
}
