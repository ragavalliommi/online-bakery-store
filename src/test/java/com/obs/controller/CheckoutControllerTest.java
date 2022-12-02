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

public class CheckoutControllerTest {
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	CheckoutController checkout = new CheckoutController();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void checkOutControllerPostTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("flexRadioDefault")).thenReturn("delivery");
		when(request.getParameter("flexRadioDefault2")).thenReturn("credit");
		when(request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp")).thenReturn(rd);
		
		checkout.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void searchControllerPostTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("flexRadioDefault")).thenReturn(null);
		when(request.getParameter("flexRadioDefault2")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp")).thenReturn(rd);
		
		checkout.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	@Test
	public void searchControllerGetTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("flexRadioDefault")).thenReturn("delivery");
		when(request.getParameter("flexRadioDefault2")).thenReturn("credit");
		when(request.getRequestDispatcher("/WEB-INF/views/Checkout.jsp")).thenReturn(rd);
		
		checkout.doGet(request, response);

	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void searchControllerGetTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("flexRadioDefault")).thenReturn(null);
		when(request.getParameter("flexRadioDefault2")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/Checkout.jsp")).thenReturn(rd);
		
		checkout.doGet(request, response);

	
		verify(rd).forward(request, response);		
	}
}
