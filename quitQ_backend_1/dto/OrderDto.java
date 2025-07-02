package com.hexaware.quitQ_backend_1.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hexaware.quitQ_backend_1.enums.OrderStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDto {
	private Long id;
	private BigDecimal totalPrice;
	private LocalDateTime orderDate;
	private OrderStatus status;
    private BasicUserDto basicUserDto;
    private PaymentDto payment;
	private List<OrderItemDto> orderItems;

	public OrderDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public OrderDto(Long id, BigDecimal totalPrice, LocalDateTime orderDate, OrderStatus status,
			BasicUserDto basicUserDto, PaymentDto payment, List<OrderItemDto> orderItems) {
		super();
		this.id = id;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.status = status;
		this.basicUserDto = basicUserDto;
		this.payment = payment;
		this.orderItems = orderItems;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public BasicUserDto getBasicUserDto() {
		return basicUserDto;
	}

	public void setBasicUserDto(BasicUserDto basicUserDto) {
		this.basicUserDto = basicUserDto;
	}

	public PaymentDto getPayment() {
		return payment;
	}

	public void setPayment(PaymentDto payment) {
		this.payment = payment;
	}

	public List<OrderItemDto> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDto> orderItems) {
		this.orderItems = orderItems;
	}

	

	

}
