package com.hexaware.quitQ_backend_1.service.interf;

import com.hexaware.quitQ_backend_1.dto.OrderDto;
import com.hexaware.quitQ_backend_1.dto.OrderItemDto;
import com.hexaware.quitQ_backend_1.dto.Response;
import com.hexaware.quitQ_backend_1.request.OrderItemRequest;

public interface OrderItemService {

	Response deleteOrderItem(Long orderItemId);

	Response getAllOrderItems();

	Response getOrderItemsByOrderId(Long orderId);
}
