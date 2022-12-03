package com.obs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManagerImpl;
import com.obs.model.Cart;
import com.obs.model.Order;


/**
 * Servlet implementation class OrderHistoryController
 */
@WebServlet("/orderHistory")
public class OrderHistoryController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    private DbManagerImpl orderHistoryDao = DbManagerImpl.getInstance();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		List<Order> ordersList = null;
		
		try {
			ordersList =  getOrderHistoryDetails(userID);
			request.setAttribute("order_history", ordersList);	
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderHistory.jsp");
		requestDispatcher.forward(request, response);
	}
	
	
	private List<Order> getOrderHistoryDetails(String userID) throws Exception {
		List<Order> listOfOrders = orderHistoryDao.getOrderHistory(userID);
		return listOfOrders;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		
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
		String orderID = request.getParameter("orderID");
		Cart cart = new Cart();
		
		if (request.getParameter("userName") != null) {
			try {
				cart = getOrderDetails(userID, orderID);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		request.setAttribute("cart_data", cart.getCartItems());
		request.setAttribute("cart_value", cart.getCartValue());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp");
		requestDispatcher.forward(request, response);

	}
	
	private Cart getOrderDetails(String userID, String orderID) throws Exception {
		Cart cart = orderHistoryDao.getOrder(userID, orderID);
		return cart;
	}

}