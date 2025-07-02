package com.hexaware.quitQ_backend_1.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "products")
public class Product {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @NotBlank(message = "Product name is required")
	 @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters")
	 private String productName;
	 
	 @NotBlank(message = "Product description is required")
	 @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters")
	 private String description;
	 
	 @NotNull(message = "Price is required")
	 private BigDecimal price;
	 
	 @NotBlank(message = "Brand is required")
	 private String brand;

	 private String imageUrl;
	 
	 private int inventory;
	 
	 @CreationTimestamp
	 @Column(name = "created_date", updatable = false)
	 private LocalDateTime createdAt;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name="category_id")
	 private Category category;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	 @JoinColumn(name = "seller_id")
	 private User seller;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Long id,
			@NotBlank(message = "Product name is required") @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters") String productName,
			@NotBlank(message = "Product description is required") @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters") String description,
			@NotNull(message = "Price is required") BigDecimal price,
			@NotBlank(message = "Brand is required") String brand, String imageUrl,
			@NotBlank(message = "Inventory is required") int inventory, LocalDateTime createdAt, Category category,
			User seller) {
		super();
		this.id = id;
		this.productName = productName;
		this.description = description;
		this.price = price;
		this.brand = brand;
		this.imageUrl = imageUrl;
		this.inventory = inventory;
		this.createdAt = createdAt;
		this.category = category;
		this.seller = seller;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	
	
	 
	 
}
