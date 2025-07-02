package com.hexaware.quitQ_backend_1.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexaware.quitQ_backend_1.enums.Gender;
import com.hexaware.quitQ_backend_1.enums.Role;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	private Long id;
    private String name;
    private Gender gender;
    private String email;
    @JsonIgnore
    private String password;
    private String phoneNumber;
    private Role role;
    private LocalDateTime registrationDate;
    private AddressDto address;
    private CartDto cart;
    private List<OrderDto> orderItemList;
    private SellerDetailsDto sellerDetails;
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(Long id, String name, Gender gender, String email, String password, String phoneNumber, Role role,
			LocalDateTime registrationDate, AddressDto address, CartDto cart, List<OrderDto> orderItemList,
			SellerDetailsDto sellerDetails) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.registrationDate = registrationDate;
		this.address = address;
		this.cart = cart;
		this.orderItemList = orderItemList;
		this.sellerDetails = sellerDetails;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public LocalDateTime getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(LocalDateTime registrationDate) {
		this.registrationDate = registrationDate;
	}
	public AddressDto getAddress() {
		return address;
	}
	public void setAddress(AddressDto address) {
		this.address = address;
	}
	public CartDto getCart() {
		return cart;
	}
	public void setCart(CartDto cart) {
		this.cart = cart;
	}
	public List<OrderDto> getOrderDtos() {
		return orderItemList;
	}
	public void setOrderDtos(List<OrderDto> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public SellerDetailsDto getSellerDetails() {
		return sellerDetails;
	}
	public void setSellerDetails(SellerDetailsDto sellerDetails) {
		this.sellerDetails = sellerDetails;
	}
	
    
    
    

}
