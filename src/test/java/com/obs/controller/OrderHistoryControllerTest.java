package com.obs.controller;

import static org.mockito.Mockito.verify;
import java.sql.ResultSet;
import static org.mockito.Mockito.when;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class OrderHistoryControllerTest {
	

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	@Mock
	ResultSet rs;
	
	OrderHistoryController orderHistory = new OrderHistoryController();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void orderHistoryControllerPostTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("orderID")).thenReturn("1");
		when(request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp")).thenReturn(rd);
		when(request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp")).thenReturn(rd);
		orderHistory.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void orderHistoryControllerPostTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("orderID")).thenReturn(null);
		when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
		when(request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp")).thenReturn(rd);
		
		orderHistory.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	@Test
	public void orderHistoryControllerGetTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getRequestDispatcher("/WEB-INF/views/OrderHistory.jsp")).thenReturn(rd);
		
		orderHistory.doGet(request, response);
		when(rs.next()).thenReturn(true).thenReturn(true).thenReturn(false);
	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void orderHistoryControllerGetTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/OrderHistory.jsp")).thenReturn(rd);
		
		orderHistory.doGet(request, response);

	
		verify(rd).forward(request, response);		
	}
}
