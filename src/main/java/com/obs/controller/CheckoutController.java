package com.obs.controller;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.Cart;
import com.obs.model.User;

/**
 * Servlet implementation class ChcekoutController
 */
@WebServlet("/order")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//checkout button - check out page render
		
		// set credentials
				if (request.getParameter("userID") != null) {
					request.setAttribute("userID", request.getParameter("userID"));
				}else {
					request.setAttribute("userID", null);
					
				}
				
				if (request.getParameter("userName") != null) {
					
					request.setAttribute("userName", request.getParameter("userName"));
				}else {
					request.setAttribute("userName", null);
					
				}
				
				String userID = request.getParameter("userID");
				
				try {
					User user = getUserDetails(userID);
					request.setAttribute("user", user);
					Cart cart = getShoppingCart(userID);
					request.setAttribute("cart_data", cart.getCartItems());
					request.setAttribute("cart_value", request.getParameter("value"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/Checkout.jsp");
				requestDispatcher.forward(request, response);
	}
	
	private User getUserDetails(String userID) throws Exception {
		User user = null;
		DbConnector dbManagerInstance = DbConnector.getInstance();
		try {
			user = dbManagerInstance.getUserByID(userID);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
		return user;
	}
	
	private Cart getShoppingCart(String userID) throws Exception {
		Cart cart = null;
		DbConnector dbManagerInstance = DbConnector.getInstance();
		try {
			cart = dbManagerInstance.getCart(userID);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
		return cart;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//place order button - order save - payment page render
		
		// set credentials
		if (request.getParameter("userID") != null) {
			request.setAttribute("userID", request.getParameter("userID"));
		}else {
			request.setAttribute("userID", null);
			
		}
		
		if (request.getParameter("userName") != null) {
			
			request.setAttribute("userName", request.getParameter("userName"));
		}else {
			request.setAttribute("userName", null);
			
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp");
        requestDispatcher.forward(request, response);
		
	}

}
