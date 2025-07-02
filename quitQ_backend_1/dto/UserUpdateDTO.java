package com.hexaware.quitQ_backend_1.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexaware.quitQ_backend_1.enums.Gender;
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserUpdateDTO {
	private String name;
    private String email;
    private String phoneNumber;
    private Gender gender;
	public UserUpdateDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserUpdateDTO(String name, String email, String phoneNumber, Gender gender) {
		super();
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
    
    

}
