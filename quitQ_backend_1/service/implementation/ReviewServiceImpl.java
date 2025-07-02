package com.hexaware.quitQ_backend_1.service.implementation;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.ReviewDto;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.Review;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.repository.ProductRepository;
import com.hexaware.quitQ_backend_1.repository.ReviewRepository;
import com.hexaware.quitQ_backend_1.repository.UserRepository;
import com.hexaware.quitQ_backend_1.service.interf.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewRepository reviewRepository;
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public Review saveReview(ReviewDto reviewDto) {
		// Fetch related product and user
		Product product = productRepository.findById(reviewDto.getProductId()).orElse(null);
		User user = userRepository.findById(reviewDto.getUserId()).orElse(null);

		if (product == null || user == null) {
			throw new RuntimeException("Invalid Product or User ID");
		}

		// Map to entity
		Review review = new Review();
		review.setContent(reviewDto.getComment());
		review.setRating(reviewDto.getRating());
		review.setProduct(product);
		review.setUser(user);
		review.setReviewDate(LocalDateTime.now());

		return reviewRepository.save(review);
	}

	@Override
	public List<Review> getReviewsByProductId(Long productId) {
		// TODO Auto-generated method stub
		return reviewRepository.findByProductId(productId);
	}

}
