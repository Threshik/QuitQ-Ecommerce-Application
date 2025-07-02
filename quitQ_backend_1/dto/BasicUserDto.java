package com.hexaware.quitQ_backend_1.dto;

import com.hexaware.quitQ_backend_1.enums.Role;

public class BasicUserDto {

	private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private Role role;
	public BasicUserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BasicUserDto(Long id, String name, String email, String phoneNumber, Role role) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
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
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
    
    
}
