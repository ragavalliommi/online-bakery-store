package com.obs.model;


import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.obs.model.User;

public class UserTest {
	
	
	static User user1;
	static User user2;
	static User user3;
	static User user4;
		


	@BeforeClass
	public static void init() {
		user1 = new User("Test User", "1234567890", "testuser@test.com", "testUserPassword");
		user2 = new User("testuser@gmail.com");
		user3 = new User("1", "Test User", "1234567890", "testuser@test.com", "test Road");
		user4 = new User();
	}
	
	
	@AfterClass
	public static void clean() {
		user1 = null;
		user2 = null;
		user3 = null;
		user4 = null;
	}
	
	
	@Test
	public void testInstanceForRegisterPage() {
		assert(user1.getEmail().equals("testuser@test.com"));
		
		user1.setUserName("Test User");
		user1.setPhone("1234567890");
		user1.setPassword("testUserPassword");
		user1.setDeliveryAddress("test Road");
		
		assert(user1.getUserName().equals("Test User"));
		assert(user1.getPhone().equals("1234567890"));
		assert(user1.getPassword().equals("testUserPassword"));
		assert(user1.getDeliveryAddress().equals("test Road"));
	}

	@Test
	public void testInstanceWithUserId() {
		assert(user3.getUserID().equals("1"));
	}
	
	@Test
	public void testVerifyMethod() {
		String password = "testUserPassword";
		
		assert(user1.verify(password) == true);
	}
	
	@Test
	public void testInstanceSetAndGet() {
		user4.setUserID("1");
		user4.setEmail("testuser@test.com");
		user4.setPassword("testUserPassword");
		user4.setDeliveryAddress("test Road");
		user4.setPhone("1234567890");
		user4.setUserName("Test User");
		
		
		assert(user4.getUserID().equals("1"));
		assert(user4.getEmail().equals("testuser@test.com"));
		assert(user4.getPassword().equals("testUserPassword"));
		assert(user4.getDeliveryAddress().equals("test Road"));
		assert(user4.getPhone().equals("1234567890"));
		assert(user4.getUserName().equals("Test User"));
		
	}
}