package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.OrderDto;
import com.hexaware.quitQ_backend_1.dto.PaymentDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.entities.Payment;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;
import com.hexaware.quitQ_backend_1.enums.PaymentMethod;
import com.hexaware.quitQ_backend_1.request.OrderRequest;

public interface OrderService {
	Response getAllOrders(); // For admin to see all orders

	Response placeOrder(Long userId, PaymentDto paymentDto);

	Response getOrder(Long orderId);

	Response getUserOrders(Long userId);

	Response updateOrderStatus(Long orderId, OrderStatus status); // Admin/Seller updates order status

	Response cancelOrder(Long orderId, Long userId);

	Response getSellerOrderSummary(Long sellerId);

	Response getSellerOrders(Long sellerId);

	Long getOrderCount();
}
