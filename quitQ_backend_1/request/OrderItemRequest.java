package com.hexaware.quitQ_backend_1.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class OrderItemRequest {

	@NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Price is required")
    private BigDecimal price;

    @NotNull(message = "Product ID is required")
    private Long productId;

	public OrderItemRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderItemRequest(@NotNull(message = "Quantity is required") Integer quantity,
			@NotNull(message = "Price is required") BigDecimal price,
			@NotNull(message = "Product ID is required") Long productId) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
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

