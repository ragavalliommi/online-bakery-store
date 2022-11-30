package com.obs.controller;


import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
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
    
	private static final String GET_ORDER_NUMBER = 
			"SELECT MAX(entryID) as entryID FROM `Orders`;";
	
	private static final String ADD_ORDER = 
			"INSERT INTO `Orders` (orderID, userID, bakeryItemID, quantity, amount, deliverymode) VALUES (?, ?, ?, ?, ?, ?);";
	
	private static final String DELETE_CART = 
			"DELETE FROM `Carts` WHERE `UserID`=?";
	
	private DbConnector orderDao = DbConnector.getInstance();
	
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
		
		String userID = request.getParameter("userID");
		
		try {
			Cart cart = getShoppingCart(userID);
			ArrayList<CartItem> cartList = cart.getCartItems();
			int id = getOrderNumber() + 1;
			for(CartItem c : cartList) {
				Order order = new Order();
				order.setOrderId(id);
				order.setUserId(userID);
				order.setBakeryItemId(c.getBakeryItem().getBakeryItemId());
				order.setQuantity(c.getItemQty());
				order.setAmount(c.getItemQty() * c.getBakeryItem().getPrice());
				order.setDeliveryMode(request.getParameter("flexRadioDefault"));

				boolean orderInserted = insertOrder(order);
				if(!orderInserted) break;
			}
			boolean cartCleared = clearUserCart(userID);
			if(!cartCleared) System.out.println("cart not cleared");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ThankYou.jsp");
        requestDispatcher.forward(request, response);
		
	}
	
	private int getOrderNumber() throws SQLException{
		int maxEntryID = 0;
		
		try(PreparedStatement ps = orderDao.getConnection().prepareStatement(GET_ORDER_NUMBER);){
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				maxEntryID = rs.getInt("entryID");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return maxEntryID;
	}
	
	private boolean insertOrder(Order order) throws SQLException{
		boolean isOrderInserted = false;
		
		try(PreparedStatement ps = orderDao.getConnection().prepareStatement(ADD_ORDER);){
			ps.setInt(1, order.getOrderId());
			ps.setString(2, order.getUserId());
			ps.setInt(3, order.getBakeryItemId());
			ps.setInt(4, order.getQuantity());
			ps.setFloat(5, order.getAmount());
			ps.setString(6, order.getDeliveryMode());
			System.out.println(ps);
			ps.executeUpdate();
			isOrderInserted = true;	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isOrderInserted;
	}
	
	
	private boolean clearUserCart(String userID) throws Exception{
		boolean isDeleted = false;
		try(PreparedStatement ps = orderDao.getConnection().prepareStatement(DELETE_CART)){
			ps.setInt(1, Integer.parseInt(userID));
			ps.executeUpdate();
			isDeleted = true;	
		}
		catch (SQLException e) {
			isDeleted = false;
			throw new SQLException(e);
		}
		return isDeleted;
	}

}
