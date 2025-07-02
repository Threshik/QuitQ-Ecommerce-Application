package com.hexaware.quitQ_backend_1.dto;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartDto {
	private Long cartId;
	private BasicUserDto basicUserDto;
	private List<CartItemDto> items;
	private BigDecimal totalAmount;

	public CartDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartDto(Long cartId, BasicUserDto basicUserDto, List<CartItemDto> items, BigDecimal totalAmount) {
		super();
		this.cartId = cartId;
		this.basicUserDto = basicUserDto;
		this.items = items;
		this.totalAmount = totalAmount;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public BasicUserDto getBasicUserDto() {
		return basicUserDto;
	}

	public void setBasicUserDto(BasicUserDto basicUserDto) {
		this.basicUserDto = basicUserDto;
	}

	public List<CartItemDto> getItems() {
		return items;
	}

	public void setItems(List<CartItemDto> items) {
		this.items = items;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	

}
