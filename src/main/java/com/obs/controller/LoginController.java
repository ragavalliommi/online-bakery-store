package com.obs.controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManagerImpl;
import com.obs.model.User;


@WebServlet("/")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	DbManagerImpl loginDao = DbManagerImpl.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String endpoint = request.getServletPath();
		System.out.println("API endpoint is:" + endpoint);
		switch (endpoint) {
		case "/":
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/LoginGUI.jsp");
			requestDispatcher.forward(request, response);			
		}
		System.out.println("Rendered the page successfully!");
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String endpoint = request.getServletPath();
			switch (endpoint) {
			case "/":
				//add validation here as well
				String email = request.getParameter("email");
				System.out.println("In doPost, value of email is: " + email);
		        String password = request.getParameter("password");
		        try {
		        	if(validateUser(email, password)) {
		        		User user = loginDao.getUser(email, password);
		        		response.sendRedirect(request.getContextPath() + "/home?userID="+user.getUserID()+"&userName="+user.getUserName());
		        	}
		        	else {
		        		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/LoginFailed.jsp");
						requestDispatcher.forward(request, response);
		        	}
		        }
		        catch(Exception exception) {
		        	System.out.println("Error in validateUser method: " + exception);
		        	
		        }
				
				break;
			}

	}


	protected boolean validateUser(String email, String password) throws Exception {
		if(email!="" && password!="") {
			User user = loginDao.getUser(email, password);
			if(user.getUserID() != null) {
				return true;
			} else {
				return false;
			}
		}
		return false;
		
	}

}

