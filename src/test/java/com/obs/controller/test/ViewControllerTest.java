package com.obs.controller.test;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ViewControllerTest {
	
	@Mock
	HttpServletRequest request;
	@Mock
	HttpServletResponse response;
	
	@Mock
	RequestDispatcher rd;
	
	@Before
	protected void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
}
