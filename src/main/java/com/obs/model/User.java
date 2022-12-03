package com.obs.model;
import java.util.UUID;

final public class User {
	
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
		return this.userID;
	}

	public User setUserID(String userID) {
		this.userID = userID;
		return this;
	}

	public String getUserName() {
		return this.userName;
	}

	public User setUserName(String userName) {
		this.userName = userName;
		return this;
	}

	public String getEmail() {
		return this.email;
	}

	public User setEmail(String email) {
		this.email = email;
		return this;
	}

	public String getPassword() {
		return this.password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public User setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
		return this;
	}

	public String getPhone() {
		return this.phone;
	}

	public User setPhone(String phone) {
		this.phone = phone;
		return this;
	}
	
	public boolean verify(String password) {
		return password.equals(this.password);
	}
		
	
}
