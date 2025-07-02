package com.hexaware.quitQ_backend_1.dto;

public class OrderSummaryDto {
	private int totalOrders;
    private int pending;
    private int processing;
    private int confirmed;
    private int shipped;
    private int delivered;
    private int cancelled;
	public OrderSummaryDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public OrderSummaryDto(int totalOrders, int pending, int processing, int confirmed, int shipped, int delivered,
			int cancelled) {
		super();
		this.totalOrders = totalOrders;
		this.pending = pending;
		this.processing = processing;
		this.confirmed = confirmed;
		this.shipped = shipped;
		this.delivered = delivered;
		this.cancelled = cancelled;
	}
	public int getTotalOrders() {
		return totalOrders;
	}
	public void setTotalOrders(int totalOrders) {
		this.totalOrders = totalOrders;
	}
	public int getPending() {
		return pending;
	}
	public void setPending(int pending) {
		this.pending = pending;
	}
	public int getProcessing() {
		return processing;
	}
	public void setProcessing(int processing) {
		this.processing = processing;
	}
	public int getConfirmed() {
		return confirmed;
	}
	public void setConfirmed(int confirmed) {
		this.confirmed = confirmed;
	}
	public int getShipped() {
		return shipped;
	}
	public void setShipped(int shipped) {
		this.shipped = shipped;
	}
	public int getDelivered() {
		return delivered;
	}
	public void setDelivered(int delivered) {
		this.delivered = delivered;
	}
	public int getCancelled() {
		return cancelled;
	}
	public void setCancelled(int cancelled) {
		this.cancelled = cancelled;
	}
    
	

}
