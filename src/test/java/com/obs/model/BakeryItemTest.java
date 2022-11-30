package com.obs.model;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.obs.model.BakeryItem;

public class BakeryItemTest {
	
	
	static BakeryItem bakeryItem1;
	static BakeryItem bakeryItem2;
	static BakeryItem bakeryItem3;
	static BakeryItem bakeryItem4;
		


	@BeforeClass
	public static void init() {
		bakeryItem1 = new BakeryItem(0, "test description", "www.test.com/image.jpg", "testItem", "Regular", 10);
		bakeryItem2 = new BakeryItem(0, "www.test.com/image.jpg", "testItem", 10);
		bakeryItem3 = new BakeryItem(0);
		bakeryItem4 = new BakeryItem(0);
	}
	
	
	@AfterClass
	public static void clean() {
		bakeryItem1 = null;
		bakeryItem2 = null;
		bakeryItem3 = null;
		bakeryItem4 = null;

	}
	
	
	@Test
	public void testInstanceForViewPage() {
		assert(bakeryItem1.getBakeryItemId() == 0);
		assert(bakeryItem1.getDescription().equals("test description"));
		assert(bakeryItem1.getImageURL().equals("www.test.com/image.jpg"));
		assert(bakeryItem1.getItemName().equals("testItem"));
		assert(bakeryItem1.getItemSize().equals("Regular"));
		assert(bakeryItem1.getPrice() == 10);
	
	}
	
	
	
	@Test
	public void testInstanceForHomeAndSearchPage() {
		assert(bakeryItem2.getBakeryItemId() == 0);
		assert(bakeryItem2.getImageURL().equals("www.test.com/image.jpg"));
		assert(bakeryItem2.getItemName().equals("testItem"));
		assert(bakeryItem2.getPrice() == 10);
	
	}
	

	@Test
	public void testInstancewithBakeryId() {
		assert(bakeryItem3.getBakeryItemId() == 0);
	}
	
	
	@Test
	public void testInstanceSetAndGet() {
		bakeryItem4.setBakeryItemId(1);
		bakeryItem4.setDescription("test description");
		bakeryItem4.setImageURL("www.test.com/image.jpg");
		bakeryItem4.setItemName("testItem");
		bakeryItem4.setItemSize("Regular");
		bakeryItem4.setPrice(10);
		
		
		assert(bakeryItem4.getBakeryItemId() == 1);
		assert(bakeryItem4.getDescription().equals("test description"));
		assert(bakeryItem4.getImageURL().equals("www.test.com/image.jpg"));
		assert(bakeryItem4.getItemName().equals("testItem"));
		assert(bakeryItem4.getItemSize().equals("Regular"));
		assert(bakeryItem4.getPrice() == 10);
		
	}
}