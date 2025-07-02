package com.hexaware.quitQ_backend_1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.ReviewDto;
import com.hexaware.quitQ_backend_1.entities.Review;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.service.interf.ProductService;
import com.hexaware.quitQ_backend_1.service.interf.ReviewService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
	
	@Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;
    
    @PostMapping("/save")
    public ResponseEntity<Response> createReview(@RequestBody ReviewDto reviewDto) {
        Review review = reviewService.saveReview(reviewDto); 

        Response response = new Response();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Review created successfully");

        ReviewDto savedDto = EntityDtoMapper.convertToDto(review); 
        response.setReviewDto(savedDto);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<Response> getReviewsByProduct(@PathVariable Long productId) {
        List<Review> reviews = reviewService.getReviewsByProductId(productId);
        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto dto = EntityDtoMapper.convertToDto(review);
            reviewDtos.add(dto);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.OK.value());
        response.setMessage("Reviews fetched");
        response.setReviewDtosList(reviewDtos);

        return ResponseEntity.ok(response);
    }



}
