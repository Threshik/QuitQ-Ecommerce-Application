package com.hexaware.quitQ_backend_1.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemRequest {
	@NotNull(message = "Quantity is required")
	@Min(value = 1, message = "Quantity must be at least 1")
	private Integer quantity;

	@NotNull(message = "User ID is required")
    private Long userId;
	
	@NotNull(message = "Price is required")
	private BigDecimal price;

	@NotNull(message = "Product ID is required")
	private Long productId;

	public CartItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItemRequest(
			@NotNull(message = "Quantity is required") @Min(value = 1, message = "Quantity must be at least 1") Integer quantity,
			@NotNull(message = "User ID is required") Long userId,
			@NotNull(message = "Price is required") BigDecimal price,
			@NotNull(message = "Product ID is required") Long productId) {
		super();
		this.quantity = quantity;
		this.userId = userId;
		this.price = price;
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	

	
	
	
}
