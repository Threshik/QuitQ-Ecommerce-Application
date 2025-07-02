package com.hexaware.quitQ_backend_1.request;

import com.hexaware.quitQ_backend_1.enums.Gender;
import com.hexaware.quitQ_backend_1.enums.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterUserRequest {
	
	@NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    private String name;

    @NotNull(message = "Gender is required")
    private Gender gender;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Contact number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits")
    private String phoneNumber;

    @NotNull(message = "Role is required")
    private Role role;
    
    private String shopName;
    private String businessLicense;
    private String gstNumber;
    private String shopAddress;

    // Constructors
    public RegisterUserRequest() {}

	public RegisterUserRequest(
			@NotBlank(message = "Name is required") @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters") String name,
			@NotNull(message = "Gender is required") Gender gender,
			@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email,
			@NotBlank(message = "Password is required") @Size(min = 6, message = "Password must be at least 6 characters long") String password,
			@NotBlank(message = "Contact number is required") @Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be 10 digits") String phoneNumber,
			@NotNull(message = "Role is required") Role role, String shopName, String businessLicense, String gstNumber,
			String shopAddress) {
		super();
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.shopName = shopName;
		this.businessLicense = businessLicense;
		this.gstNumber = gstNumber;
		this.shopAddress = shopAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
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
