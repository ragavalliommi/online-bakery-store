package com.obs.model;

import java.util.UUID;

public class Payment {

	private String paymentId = null;
	private String paymentMethod = null;
	
	public Payment() {
		this.paymentId = UUID.randomUUID().toString();
		this.paymentMethod = "Credit";
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
	
}
