package com.hexaware.quitQ_backend_1.service.interf;

import java.util.List;

import com.hexaware.quitQ_backend_1.dto.ReviewDto;
import com.hexaware.quitQ_backend_1.entities.Review;

public interface ReviewService {
	Review saveReview(ReviewDto review);
	List<Review> getReviewsByProductId(Long productId);
	

}
