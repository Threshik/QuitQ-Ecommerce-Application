package com.hexaware.quitQ_backend_1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.service.interf.CartItemService;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired CartItemService cartItemService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/addToCart/{cartId}/{productId}/{quantity}")
    public ResponseEntity<Response> addItemToCart(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @PathVariable int quantity) {
        Response response = cartItemService.addItemToCart(cartId, productId, quantity);
        return ResponseEntity.status(201).body(response);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/removeItemFromCart/{cartId}/{productId}")
    public ResponseEntity<Response> removeItemFromCart(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        Response response = cartItemService.removeItemFromCart(cartId, productId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/updateItemQuantity/{cartId}/{productId}/{quantity}")
    public ResponseEntity<Response> updateItemQuantity(
            @PathVariable Long cartId,
            @PathVariable Long productId,
            @PathVariable int quantity) {
        Response response = cartItemService.updateItemQuantity(cartId, productId, quantity);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getCartItem/{cartId}/{productId}")
    public ResponseEntity<Response> getCartItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        Response response = cartItemService.getCartItem(cartId, productId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/getCartItem/{cartId}/{productId}")
    public ResponseEntity<Response> adminGetCartItem(
            @PathVariable Long cartId,
            @PathVariable Long productId) {
        Response response = cartItemService.getCartItem(cartId, productId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/getAllCartItems/{cartId}")
    public ResponseEntity<Response> getAllCartItems(@PathVariable Long cartId) {
        Response response = cartItemService.getAllCartItems(cartId);
        return ResponseEntity.ok(response);
    }
}
