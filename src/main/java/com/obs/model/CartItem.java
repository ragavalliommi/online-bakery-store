package com.obs.model;

public class CartItem {
	private int itemQty;
	private BakeryItem bakeryItem;
	
	public CartItem(BakeryItem bakeryItem, int itemQty) {
		this.itemQty = itemQty;
		this.bakeryItem = bakeryItem;
	}
	
	public int getItemQty() {
		return itemQty;
	}
	public void setItemQty(int itemQty) {
		this.itemQty = itemQty;
	}
	public BakeryItem getBakeryItem() {
		return bakeryItem;
	}
	public void setBakeryItem(BakeryItem bakeryItem) {
		this.bakeryItem = bakeryItem;
	}

}
