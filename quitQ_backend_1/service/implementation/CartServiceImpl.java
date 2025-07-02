package com.hexaware.quitQ_backend_1.service.implementation;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.CartDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Cart;

import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;

import com.hexaware.quitQ_backend_1.repository.CartRepository;

import com.hexaware.quitQ_backend_1.repository.UserRepository;

import com.hexaware.quitQ_backend_1.service.interf.CartService;

@Service

public class CartServiceImpl implements CartService {
	private final CartRepository cartRepository;
	private final UserRepository userRepository;
	private final EntityDtoMapper entityDtoMapper;

	public CartServiceImpl(CartRepository cartRepository, UserRepository userRepository,
			EntityDtoMapper entityDtoMapper) {
		super();
		this.cartRepository = cartRepository;
		this.userRepository = userRepository;
		this.entityDtoMapper = entityDtoMapper;
	}

	@Override
	public Response getCartByUserId(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			throw new ResourceNotFoundException("Cart not found for user id: " + userId);
		}
		CartDto cartDto = EntityDtoMapper.toCartDto(cart);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Cart retrieved successfully");
		response.setCart(cartDto);
		return response;

	}

	@Override
	public Response clearCart(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			throw new ResourceNotFoundException("Cart not found for user id: " + userId);
		}
		cart.getItems().clear();
		cart.setTotalAmount(BigDecimal.ZERO);
		cartRepository.save(cart);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Cart cleared successfully");
		response.setCart(EntityDtoMapper.toCartDto(cart));
		return response;
	}

	@Override
	public Response getTotalPrice(Long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));

		cart.updateTotalAmount();
		BigDecimal total = cart.getTotalAmount();

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Total price: " + total.toPlainString());
		return response;
	}

	@Override
	public Response initializeNewCart() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();

		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));

		Cart existingCart = cartRepository.findByUser(user);

		if (existingCart != null) {
			// Instead of clearing the cart, just return it
			Response response = new Response();
			response.setStatus(200);
			response.setMessage("Existing cart returned");
			response.setCart(EntityDtoMapper.toCartDto(existingCart));
			return response;
		}

		// Only create a new cart if one doesn't exist
		Cart newCart = new Cart();
		newCart.setUser(user);
		newCart.setTotalAmount(BigDecimal.ZERO);
		newCart.setItems(new ArrayList<>());

		Cart savedCart = cartRepository.save(newCart);

		Response response = new Response();
		response.setStatus(201);
		response.setMessage("New cart initialized successfully");
		response.setCart(EntityDtoMapper.toCartDto(savedCart));
		return response;
	}

	@Override
	public Response getCart(Long id) {
		Cart cart = cartRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Cart not found with id: " + id));

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Cart fetched successfully");
		response.setCart(EntityDtoMapper.toCartDto(cart));
		return response;
	}

}
