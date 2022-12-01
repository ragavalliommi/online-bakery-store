package com.obs.model;


public class Order {
	
	private int orderId;
	private String userId;
	private int bakeryItemId;
	private int paymentId;
	private int quantity;
	private float amount;
	private String deliveryMode;
	private String orderDate;

	public Order() {
		super();
	}

	public Order(int orderId, float amount, String orderDate, String deliveryMode) {
		super();
		this.orderId = orderId;
		this.amount = amount;
		this.orderDate = orderDate;
		this.deliveryMode = deliveryMode;
	}


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public int getBakeryItemId() {
		return bakeryItemId;
	}


	public void setBakeryItemId(int bakeryItemId) {
		this.bakeryItemId = bakeryItemId;
	}


	public int getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}


	public int getQuantity() {
		return quantity;
	}


	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public float getAmount() {
		return amount;
	}


	public void setAmount(float amount) {
		this.amount = amount;
	}


	public String getDeliveryMode() {
		return deliveryMode;
	}


	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	
	public String getOrderDate() {
		return orderDate;
	}



	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	
}
