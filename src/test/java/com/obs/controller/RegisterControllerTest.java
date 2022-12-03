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



public class RegisterControllerTest {
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher requestDispatcher;
	
	
	RegisterController registerController = new RegisterController();
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void registerControllerDoGetTest() throws Exception {

		when(request.getServletPath()).thenReturn("/register");
		when(request.getRequestDispatcher("/WEB-INF/views/UserRegister.jsp")).thenReturn(requestDispatcher);

		registerController.doGet(request, response);
		verify(requestDispatcher).forward(request, response);	
	}
	
	
	@Test
	public void postRegisterControllerTest() throws Exception {
		when(request.getServletPath()).thenReturn("/register");
		when(request.getParameter("email")).thenReturn("biswa2@gmail.com");
		when(request.getParameter("phone")).thenReturn("4987839050");
		when(request.getParameter("userName")).thenReturn("biswal");
		when(request.getParameter("password")).thenReturn("viswadip");
		when(request.getParameter("deliveryAddress")).thenReturn("Dallas");
		
		when(request.getRequestDispatcher("/WEB-INF/views/SuccessMessage.jsp")).thenReturn(requestDispatcher);
		registerController.doPost(request, response);
		//verify(requestDispatcher).forward(request, response);
	}
//	
	@Test
	public void postLoginControllerTestNullValues() throws Exception {
		when(request.getServletPath()).thenReturn("/register");
		when(request.getParameter("email")).thenReturn("biswa1@gmail.com");
		when(request.getParameter("phone")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("password")).thenReturn(null);
		when(request.getParameter("deliveryAddress")).thenReturn(null);
		
		when(request.getRequestDispatcher("/WEB-INF/views/SuccessMessage.jsp")).thenReturn(requestDispatcher);
		registerController.doPost(request, response);
	}
	
	
}
