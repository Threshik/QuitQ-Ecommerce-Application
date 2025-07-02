package com.hexaware.quitQ_backend_1.service.interf;

import java.math.BigDecimal;

import com.hexaware.quitQ_backend_1.dto.CartDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.CartItemRequest;

public interface CartService {
	Response getCartByUserId(Long userId);

	Response clearCart(Long userId);

	Response getTotalPrice(Long id);

	Response initializeNewCart();

	Response getCart(Long id);

}
