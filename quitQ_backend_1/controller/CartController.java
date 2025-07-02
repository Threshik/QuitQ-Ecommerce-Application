package com.hexaware.quitQ_backend_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.CartItemRequest;
import com.hexaware.quitQ_backend_1.service.interf.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	private final CartService cartService;

	public CartController(CartService cartService) {
		super();
		this.cartService = cartService;
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'SELLER', 'USER')")
	@GetMapping("/getCartByUserId/{userId}")
	public ResponseEntity<Response> getCartByUserId(@PathVariable Long userId) {
		return ResponseEntity.ok(cartService.getCartByUserId(userId));
	}

	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	@DeleteMapping("/clear/{userId}")
	public ResponseEntity<Response> clearCart(@PathVariable Long userId) {
		return ResponseEntity.ok(cartService.clearCart(userId));
	}

	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	@GetMapping("/getTotalPrice/{cartId}")
	public ResponseEntity<Response> getTotalPrice(@PathVariable Long cartId) {
		return ResponseEntity.ok(cartService.getTotalPrice(cartId));
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/initialize")
	public ResponseEntity<Response> initializeNewCart() {
		return ResponseEntity.ok(cartService.initializeNewCart());
	}

}
