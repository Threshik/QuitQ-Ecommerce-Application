package com.hexaware.quitQ_backend_1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerProductDetailsDTO {
	private Long sellerId;
    private String name;
    private String email;
    private String shopName;
    private String businessLicense;
    private String gstNumber;
    private String shopAddress;
	public SellerProductDetailsDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SellerProductDetailsDTO(Long sellerId, String name, String email, String shopName, String businessLicense,
			String gstNumber, String shopAddress) {
		super();
		this.sellerId = sellerId;
		this.name = name;
		this.email = email;
		this.shopName = shopName;
		this.businessLicense = businessLicense;
		this.gstNumber = gstNumber;
		this.shopAddress = shopAddress;
	}
	public Long getSellerId() {
		return sellerId;
	}
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getGstNumber() {
		return gstNumber;
	}
	public void setGstNumber(String gstNumber) {
		this.gstNumber = gstNumber;
	}
	public String getShopAddress() {
		return shopAddress;
	}
	public void setShopAddress(String shopAddress) {
		this.shopAddress = shopAddress;
	}
    
    
}
