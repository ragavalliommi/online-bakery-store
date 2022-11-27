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

/**
 * Servlet implementation class OrderDetailController
 */
@WebServlet("/orderDetail")
public class OrderDetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DbConnector orderDetailDao = DbConnector.getInstance();
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
		String orderID = request.getParameter("orderID");
		Cart cart = null;
		try {
			cart = orderDetailDao.getCart(userID, orderID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("cart_data", cart.getCartItems());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
