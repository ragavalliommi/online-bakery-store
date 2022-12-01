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

public class HomePageControllerTest {
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	
	HomePageController home = new HomePageController();
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void homePageControllerTest() throws Exception {
//		ViewController view = new ViewController();
		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("biswa");
		when(request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp")).thenReturn(rd);
		

		home.doGet(request, response);

		verify(rd).forward(request, response);

	}
	
	@Test
	public void homePageControllerNullTest() throws Exception {
//		ViewController view = new ViewController();
		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp")).thenReturn(rd);
		

		home.doGet(request, response);

		verify(rd).forward(request, response);

	}

}
