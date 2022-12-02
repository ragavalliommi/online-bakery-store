package com.obs.controller;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManager;
import com.obs.model.Cart;
import com.obs.model.CartItem;
import com.obs.model.User;

/**
 * Servlet implementation class ChcekoutController
 */
@WebServlet("/order")
public class CheckoutController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;   
	private DbManager orderDao = DbManager.getInstance();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
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
			e.printStackTrace();
		}
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/Checkout.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private User getUserDetails(String userID) throws Exception {
		User user = null;
		user = orderDao.getUserByID(userID);
		return user;
	}
	
	private Cart getShoppingCart(String userID) throws Exception {
		Cart cart = null;
		cart = orderDao.getCart(userID);
		return cart;
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
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
			Cart cart = getShoppingCart(userID);
			ArrayList<CartItem> cartList = cart.getCartItems();
			int id = getOrderID(userID) + 1;
			String deliveryMode = request.getParameter("flexRadioDefault");
			String paymentMode = request.getParameter("flexRadioDefault2");
			int paymentID = getPayID(paymentMode);
			insertAll(cartList, id, userID, paymentID, deliveryMode);
			boolean cartCleared = clearCart(userID);
			if(!cartCleared) System.out.println("cart not cleared");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp");
        requestDispatcher.forward(request, response);
		
	}
	
	private int getOrderID(String userID) throws Exception{
		int orderID = orderDao.getOrderNumber();
		return orderID;
	}
	
	private int getPayID(String paymentMode) throws Exception {
		int paymentID = orderDao.getPaymentID(paymentMode);
		return paymentID;
	}
	
	private boolean clearCart(String userID) throws Exception {
		boolean cartCleared = orderDao.clearUserCart(userID);
		return cartCleared;
	}
	
	private void insertAll(ArrayList<CartItem> cartList, int id, String userID, int paymentID, String deliveryMode) throws Exception {
		orderDao.insertAllItemsInOrder(cartList, id, userID, paymentID, deliveryMode);
	}

}