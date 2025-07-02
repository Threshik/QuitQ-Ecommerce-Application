package com.hexaware.quitQ_backend_1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.OrderItemRequest;
import com.hexaware.quitQ_backend_1.service.interf.OrderItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {
	private final OrderItemService orderItemService;

	public OrderItemController(OrderItemService orderItemService) {
		super();
		this.orderItemService = orderItemService;
	}

	@DeleteMapping("/deleteOrderItem/{orderItemId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Response> deleteOrderItem(@PathVariable Long orderItemId) {
		return ResponseEntity.ok(orderItemService.deleteOrderItem(orderItemId));
	}

	@GetMapping("/getAllOrderItems")
	@PreAuthorize("hasAnyRole('USER','ADMIN','SELLER')")
	public ResponseEntity<Response> getAllOrderItems() {
		return ResponseEntity.ok(orderItemService.getAllOrderItems());
	}

	@GetMapping("/getOrderItemsByOrderId/{orderId}")
	@PreAuthorize("hasAnyRole('USER','ADMIN','SELLER')")
	public ResponseEntity<Response> getOrderItemsByOrderId(@PathVariable Long orderId) {
		return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
	}

}
