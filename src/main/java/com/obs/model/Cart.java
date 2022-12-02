package com.obs.model;

import java.util.ArrayList;

public class Cart {

	private float cartValue;
	private ArrayList<CartItem> cartItems;
	
	public Cart() {
		this.cartValue = 0;
		this.cartItems = new ArrayList<CartItem>();
	}
	
	public Cart(float cartValue, ArrayList<CartItem> cartItems) {
		this.cartValue = cartValue;
		this.cartItems = cartItems;
	}
	
	public float getCartValue() {
		return cartValue;
	}

	public void setCartValue(float cartValue) {
		this.cartValue = cartValue;
	}
	
	public ArrayList<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(ArrayList<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
		
	public boolean addItem(CartItem item) {
		this.cartItems.add(item);
		this.cartValue += item.getBakeryItem().getPrice() * item.getItemQty();
		return true;	
	}
	
	public boolean removeItem(CartItem item) {
		if(this.cartItems.contains(item))
		{
			this.cartItems.remove(item);
			this.cartValue -= item.getBakeryItem().getPrice() * item.getItemQty();
			return true;
		}
		return false;
	}
}
