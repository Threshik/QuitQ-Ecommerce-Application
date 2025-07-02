package com.hexaware.quitQ_backend_1.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Category;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.Role;

public interface ProductRepository extends JpaRepository<Product, Long>{
	List<Product> findByCategory(Category category);
	List<Product> findByBrand(String brand);
	int countBySellerId(Long sellerId);
	List<Product> findBySellerId(Long sellerId);
	

}
