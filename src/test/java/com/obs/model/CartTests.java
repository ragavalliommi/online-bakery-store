package com.obs.model;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.obs.model.*;

public class CartTests {
	static Cart cart;
	
	@BeforeClass
	public static void setup() {
		cart = new Cart();
		
	}
	
	@AfterClass
	public static void cleanup() {
		cart = new Cart();
	}
	
	@Test
	public void getCartValueAndItemsTest_DefaultValues() {
		Cart cart = new Cart();
		assert(cart.getCartValue() == 0);
		assert(cart.getCartItems().isEmpty() == true);
	}
	
	@Test
	public void getCartValueAndItemsTest_SetValues() {
		ArrayList<CartItem> listOfCartItems = new ArrayList<CartItem>();
		listOfCartItems.add(new CartItem(new BakeryItem(1), 3));
		Cart cart = new Cart();
		cart.setCartValue(123);
		cart.setCartItems(listOfCartItems);
		assert(cart.getCartValue() == 123);
		assert(cart.getCartItems().isEmpty() == false);
		assert(cart.getCartItems().size() == 1);
	}
	
	@Test
	public void addItemTest_ShouldAddNewCartItemToCart() {
		CartItem cartItem = new CartItem(new BakeryItem(1, "", "", "", "", 2), 3);
		Cart cart = new Cart();
		boolean result = cart.addItem(cartItem);
		assert(cart.getCartValue() == 6);
		assert(cart.getCartItems().size() == 1);
		assertEquals(result, true);
	}
	
	@Test
	public void removeItemTest_ShouldRemoveItemFromCartIfPresent() {
		CartItem cartItem1 = new CartItem(new BakeryItem(1, "", "", "", "", 2), 3);
		CartItem cartItem2 = new CartItem(new BakeryItem(2, "", "", "", "", 4), 2);
		Cart cart = new Cart();
		cart.setCartValue(10);
		ArrayList<CartItem> listOfCartItems = new ArrayList<CartItem>();
		listOfCartItems.add(cartItem1);
		listOfCartItems.add(cartItem2);
		cart.setCartItems(listOfCartItems);
		boolean result = cart.removeItem(cartItem1);
		assert(cart.getCartValue() == 4);
		assert(cart.getCartItems().size() == 1);
		assertEquals(result, true);
	}
	
	@Test
	public void removeItemTest_ShouldNotRemoveItemFromCart() {
		CartItem cartItem1 = new CartItem(new BakeryItem(4, "", "", "", "", 2), 3);
		Cart cart = new Cart();
		cart.setCartValue(10);
		ArrayList<CartItem> listOfCartItems = new ArrayList<CartItem>();
		cart.setCartItems(listOfCartItems);
		boolean result = cart.removeItem(cartItem1);
		assertEquals(result, false);
	}
	
}
