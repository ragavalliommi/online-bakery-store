package com.obs.controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.User;


@WebServlet("/")
public class LoginController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String endpoint = request.getServletPath();
		System.out.println("API endpoint is:" + endpoint);
		try {
			switch (endpoint) {
			case "/":
				renderLoginGUI(request, response);
				break;
			}
			System.out.println("Rendered the page successfully!");
		} catch (Exception e) {
			System.out.println("Login Page rendering unsuccessful.");
			throw new ServletException(e);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String endpoint = request.getServletPath();
		try {
			switch (endpoint) {
			case "/":
				//add validation here as well
				String email = request.getParameter("email");
				System.out.println("In doPost, value of email is: " + email);
		        String password = request.getParameter("password");
				User user = validateUser(email, password);
				if(user.getUserID() != null) {
					System.out.println("No error!");
					response.sendRedirect(request.getContextPath() + "/home?userID="+user.getUserID()+"&userName="+user.getUserName());
					System.out.println("Completed doGet");
				} else {
					System.out.println("error in doPost!");
					response.sendRedirect(request.getContextPath() + "/");	
				}
				break;
			}
		} catch (Exception e) {
			System.out.println("InCompleted doGet");
			throw new ServletException(e);
		}

	}

	private void renderLoginGUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			System.out.println("Reached LoginUI");
			String loginPath = "/WEB-INF/views/LoginGUI.jsp";
			RequestDispatcher loginDispatcher = request.getRequestDispatcher(loginPath);
			System.out.println("Dispatch login UI");
			loginDispatcher.forward(request, response);
		} catch (Exception e) {
			System.out.println("Exception:" +e);
			throw new Exception(e);
		}
	}

	private User validateUser(String email, String password) throws Exception {
		DbConnector db = DbConnector.getInstance();
		User user;
		try {
			user = db.getUser(email, password);
		} catch (Exception e) {
			throw new Exception(e);
		}
		return user;
	}

}

