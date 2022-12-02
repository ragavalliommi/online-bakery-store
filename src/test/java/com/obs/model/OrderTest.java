package com.obs.model;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.obs.model.Order;

public class OrderTest {
	
	static Order order1;
	static Order order2;
	
	@BeforeClass
	public static void init() {
		order1 = new Order();
		order2 = new Order(2, 25.00f, "2022-11-27 09:48:08", "delivery");
	}
	
	@AfterClass
	public static void clean() {
		order1 = null;
		order2 = null;
	}
	
	@Test
	public void testInstanceSetAndGet() {
		order1.setOrderId(1);
		order1.setUserId("3");
		order1.setBakeryItemId(10);
		order1.setPaymentId(1);
		order1.setQuantity(3);
		order1.setAmount(20.00f);
		order1.setDeliveryMode("pickup");
		order1.setOrderDate("2022-11-27 09:48:08");
		
		assert(order1.getOrderId() == 1);
		assert(order1.getUserId().equals("3"));
		assert(order1.getBakeryItemId() == 10);
		assert(order1.getPaymentId() == 1);
		assert(order1.getQuantity() == 3);
		assert(order1.getAmount() == 20.00f);
		assert(order1.getDeliveryMode().equals("pickup"));
		assert(order1.getOrderDate().equals("2022-11-27 09:48:08"));
		
	}
	
	@Test
	public void testInstanceForOrderHistoryPage() {
		assert(order2.getOrderId() == 2);
		assert(order2.getAmount() == 25.00f);
		assert(order2.getDeliveryMode().equals("delivery"));
		assert(order2.getOrderDate().equals("2022-11-27 09:48:08"));
	}

}