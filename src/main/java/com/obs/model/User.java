package com.obs.model;
import java.util.UUID;

public class User {
	
	private String userID;
	private String userName;
	private String phone;
	private String email;
	private String password;
	private String deliveryAddress;
	

	public User(String userName, String phone, String email, String password) {
		this.userID = UUID.randomUUID().toString();
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.password = password; // we are not hashing the password
	}
	
	public User(String email) {
		this.userID = null;
		this.userName = null;
		this.phone = null;
		this.email = email;
		this.password = null; // we are not hashing the password
	}
	
	
	public User(String userID, String userName, String phone, String email, String deliveryAddress) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.phone = phone;
		this.email = email;
		this.deliveryAddress = deliveryAddress;
	}
	
	public User() {
		this.userID = null;
		this.userName = null;
		this.phone = null;
		this.email = null;
		this.password = null;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public boolean verify(String password) {
		return password.equals(this.password);
	}
		
	
}
