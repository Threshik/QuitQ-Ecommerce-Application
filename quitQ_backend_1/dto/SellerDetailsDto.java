package com.hexaware.quitQ_backend_1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerDetailsDto {
	
	private String shopName;
    private String businessLicense;
    private String gstNumber;
    private String shopAddress;
	public SellerDetailsDto(String shopName, String businessLicense, String gstNumber, String shopAddress) {
		super();
		this.shopName = shopName;
		this.businessLicense = businessLicense;
		this.gstNumber = gstNumber;
		this.shopAddress = shopAddress;
	}
	public SellerDetailsDto() {
		super();
		// TODO Auto-generated constructor stub
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
