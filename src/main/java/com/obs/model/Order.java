package com.obs.model;


public class Order {
	
	private int orderId;
	private String userId;
	private int bakeryItemId;
	//private String paymentId = null;
	private int quantity;
	private float amount;
	private String deliveryMode;
	

	public Order() {
		super();
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


//	public String getPaymentId() {
//		return paymentId;
//	}
//
//
//	public void setPaymentId(String paymentId) {
//		this.paymentId = paymentId;
//	}


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
	

}
