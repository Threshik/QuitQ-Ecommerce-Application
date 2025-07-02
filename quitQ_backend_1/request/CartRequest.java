package com.hexaware.quitQ_backend_1.request;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CartRequest {
	@NotNull(message = "User ID is required")
	private Long userId;
	

	@NotEmpty(message = "Cart must have at least one item")
	private List<CartItemRequest> items;

	public CartRequest() {
	}

	public CartRequest(Long userId, List<CartItemRequest> items) {
		this.userId = userId;
		this.items = items;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<CartItemRequest> getItems() {
		return items;
	}

	public void setItems(List<CartItemRequest> items) {
		this.items = items;
	}

}
