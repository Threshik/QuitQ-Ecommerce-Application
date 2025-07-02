package com.hexaware.quitQ_backend_1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.dto.SellerUpdateDTO;
import com.hexaware.quitQ_backend_1.dto.UserUpdateDTO;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	private final UserService userService;

	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/get-all")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> getAllUsers() {
		return ResponseEntity.ok(userService.getAllUsers());
	}

	@GetMapping("/getUserById/{id}")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'SELLER')")
	public ResponseEntity<Response> getUserById(@PathVariable Long id) {
		return ResponseEntity.ok(userService.getUserById(id));
	}
	
	@PutMapping("/update/{id}")
	@PreAuthorize("hasAnyRole('USER', 'SELLER', 'ADMIN')")
	public ResponseEntity<Response> updateUser(
	        @PathVariable Long id,
	        @RequestBody UserUpdateDTO userUpdateDTO) {
	    return ResponseEntity.ok(userService.updateUser(id, userUpdateDTO));
	}

	@PutMapping("/update-seller/{id}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Response> updateSellerDetails(
	        @PathVariable Long id,
	        @RequestBody SellerUpdateDTO sellerUpdateDTO) {
	    return ResponseEntity.ok(userService.updateSeller(id, sellerUpdateDTO));
	}



}
