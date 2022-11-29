package com.obs.model;

public class BakeryItem {
	
	private int bakeryItemId;
	private String description;
	private String imageURL;
	private String itemName;
	private String itemSize;
	private float price;
	
	public BakeryItem(int bakeryItemId, String description, String imageURL, String itemName, String itemSize,
			float price) {
		super();
		this.bakeryItemId = bakeryItemId;
		this.description = description;
		this.imageURL = imageURL;
		this.itemName = itemName;
		this.itemSize = itemSize;
		this.price = price;
	}

	
	public BakeryItem(int bakeryItemId, String imageURL, String itemName, float price) {
		super();
		this.bakeryItemId = bakeryItemId;
		this.imageURL = imageURL;
		this.itemName = itemName;
		this.price = price;
	}
	
	
	public BakeryItem(int bakeryItemId) {
		super();
		this.bakeryItemId = bakeryItemId;
	}

	public int getBakeryItemId() {
		return bakeryItemId;
	}
	public void setBakeryItemId(int bakeryItemId) {
		this.bakeryItemId = bakeryItemId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageURL() {
		return imageURL;
	}
	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemSize() {
		return itemSize;
	}
	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	
}
