package fon.master.nst.shoppingcart.repository;

import fon.master.nst.shoppingcart.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByUsername(String username);

    ShoppingCart findByCartItemItemId(Long itemId);

}
