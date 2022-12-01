package com.obs.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.obs.model.User;





public class LoginControllerTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher requestDispatcher;
	
	
	LoginController loginController = new 	LoginController();
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void loginControllerTest() throws Exception {

		when(request.getServletPath()).thenReturn("/");
		when(request.getRequestDispatcher("/WEB-INF/views/LoginGUI.jsp")).thenReturn(requestDispatcher);

		loginController.doGet(request, response);
		verify(requestDispatcher).forward(request, response);	
	}
	
	
	@Test
	public void postLoginControllerTest() throws Exception {
		when(request.getServletPath()).thenReturn("/");
		when(request.getParameter("email")).thenReturn("biswa@gmail.com");
		when(request.getParameter("password")).thenReturn("viswadip");
		
		when(request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp")).thenReturn(requestDispatcher);
		loginController.doPost(request, response);
		verify(requestDispatcher).forward(request, response);
	}
	
	@Test 
	public void validateUserTest() throws Exception {
		User testUser = new User();
		testUser.setEmail("biswa@gmail.com");
		testUser.setPassword("viswadip");
		testUser.setUserName("biswa");
		testUser.setDeliveryAddress("Dallas");
		testUser.setPhone("4987839050");
		User user = loginController.validateUser("biswa@gmail.com", "viswadip");
		assert(user.getUserID().equals("2"));
		assert(user.getEmail().equals(testUser.getEmail()));
		assert(user.getPassword().equals(testUser.getPassword()));
		assert(user.getUserName().equals(testUser.getUserName()));
	}
	
	
}