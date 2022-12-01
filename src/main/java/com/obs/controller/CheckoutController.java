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
import com.obs.model.Order;
import com.obs.model.User;

/**
 * Servlet implementation class ChcekoutController
 */
@WebServlet("/order")
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private DbManager orderDao = DbManager.getInstance();
	
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
		DbManager dbManagerInstance = DbManager.getInstance();
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
		DbManager dbManagerInstance = DbManager.getInstance();
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
		
		String userID = request.getParameter("userID");
		
		try {
			Cart cart = getShoppingCart(userID);
			ArrayList<CartItem> cartList = cart.getCartItems();
			int id = orderDao.getOrderNumber() + 1;
			String deliveryMode = request.getParameter("flexRadioDefault");
			String paymentMode = request.getParameter("flexRadioDefault2");
			int paymentID = orderDao.getPaymentID(paymentMode);
			for(CartItem c : cartList) {
				Order order = new Order();
				order.setOrderId(id);
				order.setUserId(userID);
				order.setBakeryItemId(c.getBakeryItem().getBakeryItemId());
				order.setPaymentId(paymentID);
				order.setQuantity(c.getItemQty());
				order.setAmount(c.getItemQty() * c.getBakeryItem().getPrice());
				order.setDeliveryMode(deliveryMode);

				boolean orderInserted = orderDao.insertOrder(order);
				if(!orderInserted) break;
			}
			boolean cartCleared = orderDao.clearUserCart(userID);
			if(!cartCleared) System.out.println("cart not cleared");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp");
        requestDispatcher.forward(request, response);
		
	}

}