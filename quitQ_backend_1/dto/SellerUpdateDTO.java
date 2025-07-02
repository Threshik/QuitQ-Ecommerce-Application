package com.hexaware.quitQ_backend_1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexaware.quitQ_backend_1.enums.Gender;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SellerUpdateDTO extends UserUpdateDTO{
	 	private String shopName;
	    private String gstNumber;
	    private String businessLicense;
	    private String shopAddress;
		public SellerUpdateDTO() {
			super();
			// TODO Auto-generated constructor stub
		}
		public SellerUpdateDTO(String name, String email, String phoneNumber, Gender gender) {
			super(name, email, phoneNumber, gender);
			// TODO Auto-generated constructor stub
		}
		public SellerUpdateDTO(String shopName, String gstNumber, String businessLicense, String shopAddress) {
			super();
			this.shopName = shopName;
			this.gstNumber = gstNumber;
			this.businessLicense = businessLicense;
			this.shopAddress = shopAddress;
		}
		public String getShopName() {
			return shopName;
		}
		public void setShopName(String shopName) {
			this.shopName = shopName;
		}
		public String getGstNumber() {
			return gstNumber;
		}
		public void setGstNumber(String gstNumber) {
			this.gstNumber = gstNumber;
		}
		public String getBusinessLicense() {
			return businessLicense;
		}
		public void setBusinessLicense(String businessLicense) {
			this.businessLicense = businessLicense;
		}
		public String getShopAddress() {
			return shopAddress;
		}
		public void setShopAddress(String shopAddress) {
			this.shopAddress = shopAddress;
		}
	    

}
