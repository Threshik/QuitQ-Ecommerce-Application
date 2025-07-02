package com.hexaware.quitQ_backend_1.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.OrderDto;
import com.hexaware.quitQ_backend_1.dto.PaymentDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;
import com.hexaware.quitQ_backend_1.request.OrderRequest;
import com.hexaware.quitQ_backend_1.service.interf.OrderService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
	private final OrderService orderService;
	private final UserService userService;

	public OrderController(OrderService orderService, UserService userService) {
		super();
		this.orderService = orderService;
		this.userService = userService;
	}

	@PreAuthorize("hasRole('USER')")
	@PostMapping("/placeOrder")
	public ResponseEntity<Response> placeOrder(@RequestBody PaymentDto paymentDto) {
		User user = userService.getLoginUser();
		Response response = orderService.placeOrder(user.getId(), paymentDto);
		return ResponseEntity.status(response.getStatus()).body(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getAllOrders")
	public ResponseEntity<Response> getAllOrders() {
		Response response = orderService.getAllOrders();
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
	@GetMapping("/getOrder/{orderId}")
	public ResponseEntity<Response> getOrder(@PathVariable Long orderId) {
		// Add logic in service to check seller access if ROLE_SELLER
		Response response = orderService.getOrder(orderId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/getUserOrders/{userId}")
	public ResponseEntity<Response> getUserOrders(@PathVariable Long userId) {
		Response response = orderService.getUserOrders(userId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/getUserOrders")
	public ResponseEntity<Response> getMyOrders() {
		Long userId = userService.getLoginUser().getId();
		Response response = orderService.getUserOrders(userId);
		return ResponseEntity.ok(response);
	}

	@PreAuthorize("hasAnyRole('ADMIN','SELLER')")
	@PutMapping("/updateOrderStatus/{orderId}/{status}")
	public ResponseEntity<Response> updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
		try {
			// Convert the incoming string to enum safely
			OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());

			// Delegate to service
			Response response = orderService.updateOrderStatus(orderId, orderStatus);
			return ResponseEntity.ok(response);

		} catch (IllegalArgumentException e) {
			// If status is not a valid enum, return 400 Bad Request
			Response errorResponse = new Response();
			errorResponse.setStatus(400);
			errorResponse.setMessage("Invalid order status: " + status);
			errorResponse.setTimestamp(LocalDateTime.now());

			return ResponseEntity.badRequest().body(errorResponse);
		}
	}

	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@DeleteMapping("/cancelOrder/{orderId}/{userId}")
	public ResponseEntity<Response> cancelOrder(@PathVariable Long orderId, @PathVariable Long userId) {
		Response response = orderService.cancelOrder(orderId, userId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/seller/orderSummary")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Response> getSellerOrderSummary() {
		Long sellerId = userService.getLoginUser().getId();
		Response response = orderService.getSellerOrderSummary(sellerId);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/seller/orders")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<Response> getSellerOrders() {
		Long sellerId = userService.getLoginUser().getId();
		Response response = orderService.getSellerOrders(sellerId);
		return ResponseEntity.ok(response);
	}

}
