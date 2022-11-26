package com.obs.model;


public class Payment {

	private String paymentId = null;
	private String paymentMode = null;
	
	public Payment(String paymentId, String paymentMode) {
		super();
		this.paymentId = paymentId;
		this.paymentMode = paymentMode;
	}

	public String getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	
	
}
