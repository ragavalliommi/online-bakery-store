package com.obs.model;

import java.util.ArrayList;

public class Cart {

	private float cartValue;
	private int itemQty;
	private ArrayList<BakeryItem> itemList;
	
	public Cart() {
		this.cartValue = 0;
		this.itemQty = 0;
		this.itemList = new ArrayList<BakeryItem>();
	}
	
	public Cart(float cartValue, int itemQty, ArrayList<BakeryItem> itemList) {
		super();
		this.cartValue = cartValue;
		this.itemQty = itemQty;
		this.itemList = itemList;
	}
	
	public float getCartValue() {
		return cartValue;
	}

	public void setCartValue(float cartValue) {
		this.cartValue = cartValue;
	}
	
	public int getItemQty() {
		return itemQty;
	}

	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	
	public ArrayList<BakeryItem> getItemList() {
		return itemList;
	}

	public void setItemList(ArrayList<BakeryItem> itemList) {
		this.itemList = itemList;
	}
	
	public boolean addItem(BakeryItem item) {
		BakeryItem bakeryItem = new BakeryItem(item.getBakeryItemId(), item.getPrice());
		if (!this.itemList.contains(bakeryItem)) {
			this.itemList.add(bakeryItem);
			this.cartValue += (bakeryItem.getPrice()*this.itemQty);
			return true;
		}
		return false;
	}
}
