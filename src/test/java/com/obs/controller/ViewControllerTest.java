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





public class ViewControllerTest {
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	
	ViewController view = new ViewController();
	
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void viewControllerTest() throws Exception {

		when(request.getParameter("userID")).thenReturn("1");
		when(request.getParameter("userName")).thenReturn("sneha");
		when(request.getParameter("bakeryItemID")).thenReturn("1");
		when(request.getRequestDispatcher("/WEB-INF/views/ViewItemDetails.jsp")).thenReturn(rd);
		
		view.doGet(request, response);

	
		verify(rd).forward(request, response);		
	}
	
	@Test
	public void viewControllerTestNullInput() throws Exception {

		when(request.getParameter("userID")).thenReturn(null);
		when(request.getParameter("userName")).thenReturn(null);
		when(request.getParameter("bakeryItemID")).thenReturn(null);
		when(request.getRequestDispatcher("/WEB-INF/views/ViewItemDetails.jsp")).thenReturn(rd);

		view.doGet(request, response);
		verify(rd).forward(request, response);

		
	}
	
}