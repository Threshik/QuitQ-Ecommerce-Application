package com.hexaware.quitQ_backend_1.service.interf;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.hexaware.quitQ_backend_1.dto.Response;

public interface ProductService {
	Response createProduct(Long categoryId, MultipartFile image, String name, String description, BigDecimal price,
			String brand, int inventory);

	Response updateProduct(Long productId, Long categoryId, MultipartFile image, String name, String description,
			BigDecimal price, String brand, int inventory);

	Response deleteProduct(Long productId, Long sellerId);

	Response getProductById(Long productId);

	Response getAllProducts();

	Response getProductsByCategory(Long categoryId);

	Response getSellerProductCount(Long sellerId);

	Response getSellerProducts(Long sellerId);

	Long getProductCount();
	
	Response getAllSellersWithProducts();

}
