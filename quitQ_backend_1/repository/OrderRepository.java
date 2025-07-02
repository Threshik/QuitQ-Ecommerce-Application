package com.hexaware.quitQ_backend_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long>{
	List<Order> findByUser(User user);
	List<Order> findByStatus(OrderStatus status);
	List<Order> findByUserAndStatus(User user, OrderStatus status);
	List<Order> findByUserId(Long userId);
	


}
