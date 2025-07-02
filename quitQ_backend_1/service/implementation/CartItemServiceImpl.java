package com.hexaware.quitQ_backend_1.service.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.CartItemDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Cart;
import com.hexaware.quitQ_backend_1.entities.CartItem;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.CartItemRepository;
import com.hexaware.quitQ_backend_1.repository.CartRepository;
import com.hexaware.quitQ_backend_1.repository.ProductRepository;
import com.hexaware.quitQ_backend_1.repository.UserRepository;
import com.hexaware.quitQ_backend_1.request.CartItemRequest;
import com.hexaware.quitQ_backend_1.service.interf.CartItemService;

@Service
public class CartItemServiceImpl implements CartItemService {

	private final CartItemRepository cartItemRepository;
	private final ProductRepository productRepository;
	private final CartRepository cartRepository;

	public CartItemServiceImpl(CartItemRepository cartItemRepository, ProductRepository productRepository,
			CartRepository cartRepository) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.cartRepository = cartRepository;
	}

	@Override
	public Response addItemToCart(Long cartId, Long productId, int quantity) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));

		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));

		if (quantity <= 0) {
			throw new IllegalArgumentException("Quantity must be greater than zero");
		}

		CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);

		if (cartItem != null) {
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		} else {
			cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cartItem.setUnitPrice(product.getPrice());
			cart.getItems().add(cartItem);
		}

		// Recalculate total price
		BigDecimal itemTotalPrice = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));
		cartItem.setTotalPrice(itemTotalPrice);

		cartItemRepository.save(cartItem);

		// Update and save cart total amount
		cart.updateTotalAmount();
		cartRepository.save(cart);

		Response response = new Response();
		response.setStatus(201);
		response.setMessage("Item added to cart successfully");
		response.setCart(EntityDtoMapper.toCartDto(cart));
		return response;
	}

	@Override
	public Response removeItemFromCart(Long cartId, Long productId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));

		CartItem itemToRemove = null;
		for (CartItem item : cart.getItems()) {
			if (item.getProduct().getId().equals(productId)) {
				itemToRemove = item;
				break;
			}
		}

		if (itemToRemove == null) {
			throw new ResourceNotFoundException("CartItem not found for product id: " + productId);
		}

		cart.getItems().remove(itemToRemove);
		cartItemRepository.delete(itemToRemove);

		cart.updateTotalAmount();
		cartRepository.save(cart);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Item removed successfully");
		response.setCart(EntityDtoMapper.toCartDto(cart));
		return response;
	}

	@Override
	public Response updateItemQuantity(Long cartId, Long productId, int quantity) {
		// TODO Auto-generated method stub
		if (quantity < 1) {
			throw new IllegalArgumentException("Quantity must be at least 1");
		}

		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));

		List<CartItem> items = cart.getItems();
		CartItem targetItem = null;

		for (CartItem item : items) {
			if (item.getProduct().getId().equals(productId)) {
				targetItem = item;
				break;
			}
		}

		if (targetItem == null) {
			throw new ResourceNotFoundException("CartItem not found for product id: " + productId);
		}

		targetItem.setQuantity(quantity);
		targetItem.setTotalPrice();
		cartItemRepository.save(targetItem);
		cart.updateTotalAmount();
		cartRepository.save(cart);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Item quantity updated successfully");
		response.setCart(EntityDtoMapper.toCartDto(cart));
		return response;
	}

	@Override
	public Response getCartItem(Long cartId, Long productId) {
		// TODO Auto-generated method stub
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + cartId));

		List<CartItem> items = cart.getItems();
		CartItem foundItem = null;

		for (CartItem item : items) {
			if (item.getProduct().getId().equals(productId)) {
				foundItem = item;
				break;
			}
		}

		if (foundItem == null) {
			throw new ResourceNotFoundException("CartItem not found for product id: " + productId);
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Cart item retrieved successfully");
		response.setCartItem(EntityDtoMapper.toCartItemDto(foundItem));
		return response;
	}

	@Override
	public Response getAllCartItems(Long cartId) {
		List<CartItem> cartItems = cartItemRepository.findByCartId(cartId);

		Response response = new Response();
		if (cartItems == null || cartItems.isEmpty()) {
			response.setStatus(404);
			response.setMessage("No items found in the cart");
			response.setCartItemList(new ArrayList<>());
			return response;
		}

		List<CartItemDto> cartItemDtoList = new ArrayList<>();
		for (CartItem item : cartItems) {
			CartItemDto dto = EntityDtoMapper.toCartItemDto(item);
			cartItemDtoList.add(dto);
		}

		response.setStatus(200);
		response.setMessage("Cart items retrieved successfully");
		response.setCartItemDtoList(cartItemDtoList);
		return response;
	}

}
