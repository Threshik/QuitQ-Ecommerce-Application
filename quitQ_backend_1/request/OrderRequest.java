package com.hexaware.quitQ_backend_1.request;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hexaware.quitQ_backend_1.entities.Payment;

import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {
	@NotNull(message = "Cart ID is required")
	private Long cartId;

	//private Payment paymentInfo;

	public OrderRequest() {
	}

	public OrderRequest(@NotNull(message = "Cart ID is required") Long cartId) {
		super();
		this.cartId = cartId;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	
	

}
