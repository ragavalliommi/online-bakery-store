package com.obs.model;

import java.sql.Date;

public class Order {
	
	private String orderId = null;
	private String userId = null;
	private String bakeryItemId = null;
	private String paymentId = null;
	private int quantity = 0;
	private float amount = 0.0f;
	private Date orderDate = null;
	private String deliveryMode = null;
	
	
	public Order(String orderId, String userId, String bakeryItemId, String paymentId, int quantity, float amount,
			Date orderDate, String deliveryMode) {
		super();
		this.orderId = orderId;
		this.userId = userId;
		this.bakeryItemId = bakeryItemId;
		this.paymentId = paymentId;
		this.quantity = quantity;
		this.amount = amount;
		this.orderDate = orderDate;
		this.deliveryMode = deliveryMode;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getBakeryItemId() {
		return bakeryItemId;
	}


	public void setBakeryItemId(String bakeryItemId) {
		this.bakeryItemId = bakeryItemId;
	}


	public String getPaymentId() {
		return paymentId;
	}


	public void setPaymentId(String paymentId) {
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


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public String getDeliveryMode() {
		return deliveryMode;
	}


	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}
	

}
