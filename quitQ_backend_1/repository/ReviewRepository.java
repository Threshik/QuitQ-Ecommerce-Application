package com.hexaware.quitQ_backend_1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.quitQ_backend_1.entities.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
	List<Review> findByProductId(Long productId);

}
