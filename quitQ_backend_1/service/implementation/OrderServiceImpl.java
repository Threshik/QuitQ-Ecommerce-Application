package com.hexaware.quitQ_backend_1.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.quitQ_backend_1.dto.OrderDto;
import com.hexaware.quitQ_backend_1.dto.OrderItemDto;
import com.hexaware.quitQ_backend_1.dto.OrderSummaryDto;
import com.hexaware.quitQ_backend_1.dto.PaymentDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Cart;
import com.hexaware.quitQ_backend_1.entities.CartItem;
import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.OrderItem;
import com.hexaware.quitQ_backend_1.entities.Payment;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.entities.User;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;
import com.hexaware.quitQ_backend_1.enums.PaymentMethod;
import com.hexaware.quitQ_backend_1.enums.PaymentStatus;
import com.hexaware.quitQ_backend_1.exception.ResourceNotFoundException;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.CartItemRepository;
import com.hexaware.quitQ_backend_1.repository.CartRepository;
import com.hexaware.quitQ_backend_1.repository.OrderItemRepository;
import com.hexaware.quitQ_backend_1.repository.OrderRepository;
import com.hexaware.quitQ_backend_1.repository.ProductRepository;
import com.hexaware.quitQ_backend_1.repository.UserRepository;
import com.hexaware.quitQ_backend_1.request.OrderItemRequest;
import com.hexaware.quitQ_backend_1.request.OrderRequest;
import com.hexaware.quitQ_backend_1.service.interf.OrderService;
import com.hexaware.quitQ_backend_1.service.interf.UserService;

@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private EntityDtoMapper entityDtoMapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CartItemRepository cartItemRepository;
	@Autowired
	private CartRepository cartRepository;

	@Override
	public Response getAllOrders() {
		// TODO Auto-generated method stub
		List<Order> orders = orderRepository.findAll();
		List<OrderDto> orderDtos = new ArrayList<>();
		for (Order order : orders) {
			orderDtos.add(EntityDtoMapper.toOrderDto(order));
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("All orders fetched successfully");
		response.setOrderList(orderDtos);
		return response;
	}

	@Transactional
	@Override
	public Response placeOrder(Long userId, PaymentDto paymentDto) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		Cart cart = cartRepository.findByUser(user);
		if (cart == null) {
			throw new ResourceNotFoundException("Cart not found for user id: " + userId);
		}

		if (cart.getItems().isEmpty()) {
			throw new IllegalStateException("Cart is empty, cannot place order");
		}

		Order order = new Order();
		order.setUser(user);
		order.setStatus(OrderStatus.CONFIRMED);
		order.setOrderDate(LocalDateTime.now());

		List<OrderItem> orderItems = new ArrayList<>();
		BigDecimal totalPrice = BigDecimal.ZERO;

		for (CartItem cartItem : cart.getItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

			totalPrice = totalPrice.add(orderItem.getPrice());
			orderItems.add(orderItem);
		}

		order.setOrderItems(orderItems);
		order.setTotalPrice(totalPrice);

		Payment payment = EntityDtoMapper.toPayment(paymentDto);
		payment.setOrder(order);
		payment.setAmount(totalPrice);
		payment.setPaymentMethod(paymentDto.getPaymentMethod());
		switch (paymentDto.getPaymentMethod()) {
		case CARD, NET_BANKING, UPI -> payment.setPaymentStatus(PaymentStatus.SUCCESS);
		case COD, WALLET, PAYLATER -> payment.setPaymentStatus(PaymentStatus.PENDING);
		default -> payment.setPaymentStatus(PaymentStatus.PENDING);
		}

		payment.setPaymentDate(LocalDateTime.now());

		order.setPayment(payment);

		orderRepository.save(order);

		cart.getItems().clear();
		cart.setTotalAmount(BigDecimal.ZERO);
		cartRepository.save(cart);

		Response response = new Response();
		response.setStatus(201);
		response.setMessage("Order placed successfully");
		response.setOrder((EntityDtoMapper.toOrderDto(order)));
		return response;
	}

	@Override
	public Response getOrder(Long orderId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

		User loggedInUser = userService.getLoginUser();
		String role = loggedInUser.getRole().name();

		if ("SELLER".equals(role)) {
			boolean hasOwnProduct = order.getOrderItems().stream()
					.anyMatch(item -> item.getProduct().getSeller().getId().equals(loggedInUser.getId()));

			if (!hasOwnProduct) {
				throw new AccessDeniedException("Access denied. This order does not contain your products.");
			}
		}

		// ADMIN can see any order, so no check

		OrderDto orderDto = EntityDtoMapper.toOrderDto(order);
		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Order fetched successfully");
		response.setOrder(orderDto);
		return response;
	}

	@Override
	public Response getUserOrders(Long userId) {
		// TODO Auto-generated method stub
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

		List<Order> orders = orderRepository.findByUser(user);
		List<OrderDto> orderDtos = new ArrayList<>();
		for (Order order : orders) {
			orderDtos.add(EntityDtoMapper.toOrderDto(order));
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("User orders fetched successfully");
		response.setOrderList(orderDtos);
		return response;
	}

	@Override
	public Response updateOrderStatus(Long orderId, OrderStatus status) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

		order.setStatus(status);
		orderRepository.save(order);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Order status updated successfully");
		response.setOrder(EntityDtoMapper.toOrderDto(order));
		return response;
	}

	@Transactional
	@Override
	public Response cancelOrder(Long orderId, Long userId) {
		// TODO Auto-generated method stub
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

		if (!order.getUser().getId().equals(userId)) {
			throw new AccessDeniedException("You can only cancel your own orders");
		}

		if (order.getStatus() != OrderStatus.CONFIRMED && order.getStatus() != OrderStatus.PROCESSING) {
			throw new IllegalStateException("Order cannot be cancelled at this stage");
		}

		order.setStatus(OrderStatus.CANCELLED);
		orderRepository.save(order);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Order cancelled successfully");
		response.setOrder(EntityDtoMapper.toOrderDto(order));
		return response;
	}

	@Override
	public Response getSellerOrders(Long sellerId) {
		List<Order> allOrders = orderRepository.findAll();
		List<OrderDto> sellerOrderDtos = new ArrayList<>();

		for (Order order : allOrders) {
			List<OrderItem> sellerItems = new ArrayList<>();

			for (OrderItem item : order.getOrderItems()) {
				Product product = item.getProduct();
				if (product.getSeller() != null && product.getSeller().getId().equals(sellerId)) {
					sellerItems.add(item);
				}
			}

			if (!sellerItems.isEmpty()) {
				OrderDto orderDto = EntityDtoMapper.toOrderDto(order);

				// Set only seller's items in the DTO
				List<OrderItemDto> itemDtos = new ArrayList<>();
				for (OrderItem item : sellerItems) {
					itemDtos.add(EntityDtoMapper.toOrderItemDto(item));
				}

				orderDto.setOrderItems(itemDtos);
				sellerOrderDtos.add(orderDto);
			}
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Seller orders fetched successfully.");
		response.setOrderList(sellerOrderDtos);
		return response;
	}

	@Override
	public Response getSellerOrderSummary(Long sellerId) {
		List<Order> allOrders = orderRepository.findAll();
		OrderSummaryDto summary = new OrderSummaryDto();

		int totalOrders = 0;

		for (Order order : allOrders) {
			boolean hasSellerItem = false;

			for (OrderItem item : order.getOrderItems()) {
				Product product = item.getProduct();
				if (product.getSeller() != null && product.getSeller().getId().equals(sellerId)) {
					hasSellerItem = true;
					break;
				}
			}

			if (hasSellerItem) {
				totalOrders++;

				switch (order.getStatus()) {
				case PENDING -> summary.setPending(summary.getPending() + 1);
				case PROCESSING -> summary.setProcessing(summary.getProcessing() + 1);
				case CONFIRMED -> summary.setConfirmed(summary.getConfirmed() + 1);
				case SHIPPED -> summary.setShipped(summary.getShipped() + 1);
				case DELIVERED -> summary.setDelivered(summary.getDelivered() + 1);
				case CANCELLED -> summary.setCancelled(summary.getCancelled() + 1);
				default -> {
				}
				}
			}
		}

		summary.setTotalOrders(totalOrders);

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Seller order summary retrieved successfully.");
		response.setOrderSummary(summary);
		return response;
	}

	@Override
	public Long getOrderCount() {
		return orderRepository.count();
	}

}
