package com.hexaware.quitQ_backend_1.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerProductDTO {
	private SellerProductDetailsDTO sellerDetails;
	private List<ProductDto> products;
	public SellerProductDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SellerProductDTO(SellerProductDetailsDTO sellerDetails, List<ProductDto> products) {
		super();
		this.sellerDetails = sellerDetails;
		this.products = products;
	}
	public SellerProductDetailsDTO getSellerDetails() {
		return sellerDetails;
	}
	public void setSellerDetails(SellerProductDetailsDTO sellerDetails) {
		this.sellerDetails = sellerDetails;
	}
	public List<ProductDto> getProducts() {
		return products;
	}
	public void setProducts(List<ProductDto> products) {
		this.products = products;
	}
	
	

}
