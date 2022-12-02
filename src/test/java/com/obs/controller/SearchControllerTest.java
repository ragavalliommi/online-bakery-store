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

public class SearchControllerTest {

	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	SearchController search = new SearchController();
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void searchControllerTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("searchString")).thenReturn("cho");
		when(request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp")).thenReturn(rd);
		
		search.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void searchControllerTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("searchString")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp")).thenReturn(rd);
		
		search.doPost(request, response);

	
		verify(rd).forward(request, response);		
	}
	
}
