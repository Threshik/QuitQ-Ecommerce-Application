package com.hexaware.quitQ_backend_1.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.quitQ_backend_1.dto.AddressDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.service.interf.AddressService;


@RestController
@RequestMapping("/api/address")
public class AddressController {
	private final AddressService addressService;

	public AddressController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}

	@PostMapping("/save")
	@PreAuthorize("hasAnyRole('USER', 'SELLER')")
	public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto) {
		return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto));
	}

	@GetMapping("/getAddressById/{id}")
	@PreAuthorize("hasAnyRole('USER', 'SELLER', 'ADMIN')")
	public ResponseEntity<Response> getAddressById(@PathVariable Long id) {
		return ResponseEntity.ok(addressService.getAddressById(id));
	}

	@DeleteMapping("/deleteAddress/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteAddress(@PathVariable Long id) {
		return ResponseEntity.ok(addressService.deleteAddress(id));
	}
}
