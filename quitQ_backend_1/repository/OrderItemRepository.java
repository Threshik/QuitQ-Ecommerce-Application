package com.hexaware.quitQ_backend_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.OrderItem;
import com.hexaware.quitQ_backend_1.entities.Product;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{
	List<OrderItem> findByOrder(Order order);
	List<OrderItem> findByProduct(Product product);
	long findByProductSellerId(Long sellerId);


}
