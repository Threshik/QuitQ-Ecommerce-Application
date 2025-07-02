package com.hexaware.quitQ_backend_1.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.quitQ_backend_1.dto.OrderItemDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Order;
import com.hexaware.quitQ_backend_1.entities.OrderItem;
import com.hexaware.quitQ_backend_1.entities.Product;
import com.hexaware.quitQ_backend_1.mapper.EntityDtoMapper;
import com.hexaware.quitQ_backend_1.repository.OrderItemRepository;
import com.hexaware.quitQ_backend_1.repository.OrderRepository;
import com.hexaware.quitQ_backend_1.repository.ProductRepository;
import com.hexaware.quitQ_backend_1.request.OrderItemRequest;
import com.hexaware.quitQ_backend_1.service.interf.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

	private final OrderItemRepository orderItemRepository;
	private final OrderRepository orderRepository;
	private final EntityDtoMapper entityDtoMapper;
	private final ProductRepository productRepository;

	public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderRepository orderRepository,
			EntityDtoMapper entityDtoMapper, ProductRepository productRepository) {
		super();
		this.orderItemRepository = orderItemRepository;
		this.orderRepository = orderRepository;
		this.entityDtoMapper = entityDtoMapper;
		this.productRepository = productRepository;
	}

	@Override
	public Response getAllOrderItems() {
		// TODO Auto-generated method stub
		List<OrderItem> items = orderItemRepository.findAll();
		List<OrderItemDto> itemDtoList = new ArrayList<>();
		for (OrderItem item : items) {
			itemDtoList.add(EntityDtoMapper.toOrderItemDto(item));
		}

		Response response = new Response();
		response.setStatus(200);
		response.setMessage("Order items fetched successfully");
		response.setOrderItemList(itemDtoList);
		return response;

	}

	@Override
	public Response getOrderItemsByOrderId(Long orderId) {
		// TODO Auto-generated method stub
		Optional<Order> orderOpt = orderRepository.findById(orderId);
		Response response = new Response();

		if (!orderOpt.isPresent()) {
			response.setStatus(404);
			response.setMessage("Order not found with ID: " + orderId);
			return response;
		}

		List<OrderItem> orderItems = orderItemRepository.findByOrder(orderOpt.get());

		
		response.setStatus(200);
		response.setMessage("Order items retrieved successfully");
		response.setOrderItemList(EntityDtoMapper.toOrderItemDtoList(orderItems));

		return response;
	}
	
	@Override
	public Response deleteOrderItem(Long orderItemId) {
		Response response = new Response();

		Optional<OrderItem> orderItemOpt = orderItemRepository.findById(orderItemId);
		if (!orderItemOpt.isPresent()) {
			response.setStatus(404);
			response.setMessage("Order item not found with ID: " + orderItemId);
			return response;
		}

		orderItemRepository.deleteById(orderItemId);

		response.setStatus(200);
		response.setMessage("Order item deleted successfully");
		return response;
	}

	
	
}
