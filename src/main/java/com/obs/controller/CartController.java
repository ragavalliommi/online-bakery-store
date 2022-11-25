package com.obs.controller;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.BakeryItem;
import com.obs.model.Cart;

@WebServlet("/cart")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String endpoint = request.getServletPath();
		System.out.println(endpoint);
		System.out.println("Teesttt");
		try {
			switch (endpoint) {
			case "/cart":
				if(request.getParameter("userID") != null) {
					request.setAttribute("userID", request.getParameter("userID"));	
				} else {
					request.setAttribute("userID", null);
				}
				if(request.getParameter("userName")!=null) {
					request.setAttribute("userName", request.getParameter("userName"));
				}
				else {
					request.setAttribute("userName", null);
				}
				String userID = request.getParameter("userID");
				Cart cart = getShoppingCart(userID);
				request.setAttribute("cart_data", cart.getCartItems());
				request.setAttribute("cart_value", cart.getCartValue());
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/Cart.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
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
		String endpoint = request.getServletPath();
		System.out.println(endpoint);
		try {
			switch (endpoint) {
			case "/cart":
				String userID = request.getParameter("userID");
				String bakeryItemID = request.getParameter("bakeryItemID");
				String quantity = request.getParameter("quantity");
				if(request.getParameter("userID")!=null) {
					request.setAttribute("userID", request.getParameter("userID"));
				}
				else {
					request.setAttribute("userID", null);
				}
				if(request.getParameter("userName")!=null) {
					request.setAttribute("userName", request.getParameter("userName"));
				}
				else {
					request.setAttribute("userName", null);
				}
				boolean isAddedToCart = addToCart(userID, bakeryItemID, quantity);
				System.out.println(request.getAttribute("userName"));
				Cart cart = getShoppingCart(userID);
				request.setAttribute("cart_data", cart.getCartItems());
				request.setAttribute("cart_value", cart.getCartValue());
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/Cart.jsp");
				requestDispatcher.forward(request, response);
				
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}


	private boolean addToCart(String userID, String bakeryItemID, String quantity) throws Exception {
		DbConnector dbManagerInstance = DbConnector.getInstance();
		try {
			 return dbManagerInstance.addToCart(userID, bakeryItemID, quantity);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}	
}
