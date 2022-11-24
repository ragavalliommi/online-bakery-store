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
		try {
			switch (endpoint) {
			case "/cart":
				request.setAttribute("userId", request.getParameter("userId") != null ? request.getParameter("userId") : null);
				if(request.getParameter("userID") != null) {
					request.setAttribute("userID", request.getParameter("userID"));	
				} else {
					request.setAttribute("userID", null);
				}
				if(request.getParameter("bakeryItemID") != null) {
					request.setAttribute("bakeryItemID", request.getParameter("bakeryItemID"));	
				} else {
					request.setAttribute("bakeryItemID", null);
				}
				if(request.getParameter("quantity") != null) {
					request.setAttribute("quantity", request.getParameter("quantity"));	
				} else {
					request.setAttribute("quantity", null);
				}
				String userID = request.getParameter("userID");
				Cart cart = getShoppingCart(userID);
				request.setAttribute("cart", cart);
				RequestDispatcher dispatcher = request.getRequestDispatcher("Cart.jsp");
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
				boolean isAddedToCart = addToCart(userID, bakeryItemID, quantity);
				response.sendRedirect(request.getContextPath() + "/obs/viewItemDetails?userID="+userID+"&userName="+userName+"&bakeryItemID="+bakeryItemID+"&quantity="+quantity);
				break;  
			}
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}


	private boolean addToCart(String userID, String bakeryItemID, String quantity) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
