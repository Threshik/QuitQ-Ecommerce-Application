package com.hexaware.quitQ_backend_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Cart;
import com.hexaware.quitQ_backend_1.entities.CartItem;
import com.hexaware.quitQ_backend_1.entities.Product;


public interface CartItemRepository extends JpaRepository<CartItem, Long>{
	void deleteAllByCartId(Long id);
	List<CartItem> findByCart(Cart cart); // Get all items in a cart
	CartItem findByCartAndProduct(Cart cart, Product product); //Find a specific item in a cart
	List<CartItem> findByCartId(Long cartId);
	
}
