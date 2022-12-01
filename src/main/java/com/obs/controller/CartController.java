package com.obs.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.BakeryItem;
import com.obs.model.Cart;
import com.obs.model.CartItem;

@WebServlet("/cart")
public class CartController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String endpoint = request.getServletPath();
		System.out.print(endpoint);
		try {
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
			String userID = request.getParameter("userID");
			Cart cart = null;
			RequestDispatcher dispatcher = null;
			switch (endpoint) {
			case "/cart":
				String itemID = request.getParameter("itemID");
				if(itemID != null) 
				{
					deleteItem(userID,itemID);
				}
				cart = getShoppingCart(userID);
				request.setAttribute("cart_data", cart.getCartItems());
				request.setAttribute("cart_value", cart.getCartValue());
				dispatcher = request.getRequestDispatcher("/WEB-INF/views/Cart.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	// Get User Cart
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
	
	// Delete Item from User Cart 
	public void deleteItem(String userID, String itemID) throws Exception {
		DbConnector dbManagerInstance = DbConnector.getInstance();
		try {
			dbManagerInstance.deleteItemFromCart(userID, itemID);
		}
		catch (Exception e) {
			throw new Exception(e);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String endpoint = request.getServletPath();
		System.out.println(endpoint);
		try {
			switch (endpoint) {
			case "/cart":
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
				String userID = request.getParameter("userID");
				String bakeryItemID = request.getParameter("bakeryItemID");
				String quantity = request.getParameter("quantity");
				
				boolean isAddedToCart = addToCart(userID, bakeryItemID, quantity);
				if (isAddedToCart) {
					Cart cart = getShoppingCart(userID);
					request.setAttribute("cart_data", cart.getCartItems());
					request.setAttribute("cart_value", cart.getCartValue());
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/Cart.jsp");
					requestDispatcher.forward(request, response);
				}
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}
	
	// Add To Cart
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
