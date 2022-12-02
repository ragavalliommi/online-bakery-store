package com.obs.controller;


import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CartControllerTest{
	
	@Mock
	HttpServletRequest request;
	
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	CartController cart = new CartController();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getCartControllerTest() throws Exception {
		when(request.getServletPath()).thenReturn("/cart");
		when(request.getParameter("userID")).thenReturn("4");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("itemID")).thenReturn("11");
		when(request.getParameter("quantity")).thenReturn("1");
		when(request.getRequestDispatcher("/WEB-INF/views/Cart.jsp")).thenReturn(rd);
		
		cart.doGet(request, response);
		verify(rd).forward(request, response);
	}
	
	@Test
	public void getCartControllerTestNullUserName() throws Exception {
		when(request.getServletPath()).thenReturn("/cart");
		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("itemID")).thenReturn(null);
		when(request.getParameter("quantity")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/Cart.jsp")).thenReturn(rd);
		
		cart.doGet(request, response);
		verify(rd).forward(request, response);
	}
	
//	@Test
//	public void getCartControllerTestException() throws Exception {
//
//		CartController dictCart = mock(CartController.class);
//		doThrow(ServletException.class).when(dictCart).doGet(null, response);
//		dictCart.doGet(request, response);
//	}
	
	@Test
	public void postCartControllerTest() throws Exception {
		when(request.getServletPath()).thenReturn("/cart");
		when(request.getParameter("userID")).thenReturn("4");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("bakeryItemID")).thenReturn("11");
		when(request.getParameter("quantity")).thenReturn("1");
		when(request.getRequestDispatcher("/WEB-INF/views/Cart.jsp")).thenReturn(rd);
		
		cart.doPost(request, response);
		verify(rd).forward(request, response);
	}
	
	@Test
	public void postCartControllerDuplicateEntryTest() throws Exception {
		when(request.getServletPath()).thenReturn("/cart");
		when(request.getParameter("userID")).thenReturn("4");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("bakeryItemID")).thenReturn("11");
		when(request.getParameter("quantity")).thenReturn("2");
		when(request.getRequestDispatcher("/WEB-INF/views/Cart.jsp")).thenReturn(rd);
		
		cart.doPost(request, response);
		verify(rd).forward(request, response);
	}
	
	
	@Test
	public void postCartControllerTestNullUserName() throws Exception {
		when(request.getServletPath()).thenReturn("/cart");
		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("bakeryItemID")).thenReturn(null);
		when(request.getParameter("quantity")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/Cart.jsp")).thenReturn(rd);
		
		cart.doPost(request, response);
		verify(rd).forward(request, response);
	}

}
