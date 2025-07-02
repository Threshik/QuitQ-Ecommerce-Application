package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.CartItemRequest;

public interface CartItemService {

	Response addItemToCart(Long cartId, Long productId, int quantity);

	Response removeItemFromCart(Long cartId, Long productId);

	Response updateItemQuantity(Long cartId, Long productId, int quantity);

	Response getCartItem(Long cartId, Long productId);

	Response getAllCartItems(Long cartId);

}
