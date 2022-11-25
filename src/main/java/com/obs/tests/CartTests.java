package com.obs.tests;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.obs.model.*;

public class CartTests {
	Cart cart;
	
	@BeforeClass
	public void setup() {
		cart = new Cart();
	}
	
	@AfterClass
	public void cleanup() {
		
	}
	
	@Test
	public void getCartValueTest() {
		Cart cart = new Cart();
		assert(cart.getCartValue() == 0);
		assert(cart.getCartItems().isEmpty() == true);
	}
}
