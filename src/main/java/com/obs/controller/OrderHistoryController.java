package com.obs.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.Cart;
import com.obs.model.Order;


/**
 * Servlet implementation class OrderHistoryController
 */
@WebServlet("/orderHistory")
public class OrderHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DbConnector orderHistoryDao = DbConnector.getInstance();
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
					List<Order> ordersList =  orderHistoryDao.getOrderHistory(userID);
					request.setAttribute("order_history", ordersList);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderHistory.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
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
				String orderID = request.getParameter("orderID");
				Cart cart = null;
				try {
					cart = orderHistoryDao.getCart(userID, orderID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("cart_data", cart.getCartItems());
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp");
				requestDispatcher.forward(request, response);

	}
	

}
