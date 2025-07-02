package com.hexaware.quitQ_backend_1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
	private Long id;
    private String productName;
    private String description;
    private BigDecimal price;
    private String brand;
    private int inventory;
    private String imageUrl;
    private LocalDateTime createdAt;
    private Long categoryId;
    private String categoryName;
    private SellerDetailsDto sellerDetails;
	public ProductDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ProductDto(Long id, String productName, String description, BigDecimal price, String brand, int inventory,
			String imageUrl, LocalDateTime createdAt, Long categoryId, String categoryName,
			SellerDetailsDto sellerDetails) {
		super();
		this.id = id;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.brand = brand;
		this.inventory = inventory;
		this.imageUrl = imageUrl;
		this.createdAt = createdAt;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.sellerDetails = sellerDetails;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public int getInventory() {
		return inventory;
	}
	public void setInventory(int inventory) {
		this.inventory = inventory;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public SellerDetailsDto getSellerDetails() {
		return sellerDetails;
	}
	public void setSellerDetails(SellerDetailsDto sellerDetails) {
		this.sellerDetails = sellerDetails;
	}
	
}
