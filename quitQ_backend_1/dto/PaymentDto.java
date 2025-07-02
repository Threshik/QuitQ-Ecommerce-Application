package com.hexaware.quitQ_backend_1.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hexaware.quitQ_backend_1.enums.PaymentMethod;
import com.hexaware.quitQ_backend_1.enums.PaymentStatus;

public class PaymentDto {
	private Long id;
	private BigDecimal amount;
    private PaymentMethod paymentMethod;
	private PaymentStatus paymentStatus;
	public PaymentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PaymentDto(Long id, BigDecimal amount, PaymentMethod paymentMethod, PaymentStatus paymentStatus) {
		super();
		this.id = id;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentStatus = paymentStatus;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
    
    

}
