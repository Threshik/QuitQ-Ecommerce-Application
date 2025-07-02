package com.hexaware.quitQ_backend_1.controller;

import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.service.interf.ProductService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

import jakarta.annotation.security.PermitAll;

@RestController
@RequestMapping("/product")
public class ProductController {
	private final ProductService productService;
	private final UserService userService;

	public ProductController(ProductService productService, UserService userService) {
		super();
		this.productService = productService;
		this.userService = userService;
	}

	@PostMapping("/create")
	@PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
	public ResponseEntity<Response> createProduct(@RequestParam Long categoryId, @RequestParam MultipartFile image,
			@RequestParam String name, @RequestParam String description, @RequestParam BigDecimal price,
			@RequestParam String brand, @RequestParam int inventory) {
		return ResponseEntity
				.ok(productService.createProduct(categoryId, image, name, description, price, brand, inventory));
	}

	@PutMapping("/update/{productId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
	public ResponseEntity<Response> updateProduct(@PathVariable Long productId, @RequestParam Long categoryId,
			@RequestParam MultipartFile image, @RequestParam String name, @RequestParam String description,
			@RequestParam BigDecimal price, @RequestParam String brand, @RequestParam int inventory) {
		return ResponseEntity.ok(
				productService.updateProduct(productId, categoryId, image, name, description, price, brand, inventory));
	}

	@GetMapping("/getProductById/{productId}")
	// @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	@PermitAll
	public ResponseEntity<Response> getProductById(@PathVariable Long productId) {
		Response response = productService.getProductById(productId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getAllProducts")
	// @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	@PermitAll
	public ResponseEntity<Response> getAllProducts() {
		Response response = productService.getAllProducts();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/getProductsByCategory/{categoryId}")
	// @PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	@PermitAll
	public ResponseEntity<Response> getProductsByCategory(@PathVariable Long categoryId) {
		Response response = productService.getProductsByCategory(categoryId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/seller/productCount")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Response> getSellerProductCount() {
		Long sellerId = userService.getLoginUser().getId();
		return ResponseEntity.ok(productService.getSellerProductCount(sellerId));
	}

	@GetMapping("/seller/products")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Response> getSellerProducts() {
		Long sellerId = userService.getLoginUser().getId();
		return ResponseEntity.ok(productService.getSellerProducts(sellerId));
	}

	@DeleteMapping("/deleteProduct/{productId}")
	@PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
	public ResponseEntity<Response> deleteProduct(@PathVariable Long productId) {
		Long sellerId = userService.getLoginUser().getId();
		return ResponseEntity.ok(productService.deleteProduct(productId, sellerId));
	}
	
	@GetMapping("/admin/sellers-with-products")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> getAllSellersWithProducts() {
	    return ResponseEntity.ok(productService.getAllSellersWithProducts());
	}

}
