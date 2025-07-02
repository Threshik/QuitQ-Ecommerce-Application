package com.hexaware.quitQ_backend_1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Cart;
import com.hexaware.quitQ_backend_1.entities.User;

public interface CartRepository extends JpaRepository<Cart, Long>{
	Cart findByUser(User user);
	Cart findByUserId(Long userId);


}
