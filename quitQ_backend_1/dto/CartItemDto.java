package com.hexaware.quitQ_backend_1.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CartItemDto {
	private Long itemId;
	private Integer quantity;
	private BigDecimal unitPrice;
	private ProductDto product;

	public CartItemDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CartItemDto(Long itemId, Integer quantity, BigDecimal unitPrice, ProductDto product) {
		super();
		this.itemId = itemId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.product = product;
	}

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	

}
