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
	
	//Connector
	DbConnector cartDao = DbConnector.getInstance();
	
	private static final String VIEW_ITEM = 
			"SELECT * FROM `BakeryItems` WHERE  `BakeryItemID` = ? ";
	
	// cart queries
	private static final String GET_CART = 
			"SELECT `BakeryItemID`,`ItemQuantity` FROM `Carts` WHERE `UserID`=?";
	private static final String ADD_CART = 
			"INSERT INTO `Carts`(`UserID`, `BakeryItemID`, `ItemQuantity`, `ItemAmount`) values (?, ?, ?, ?)";
	private static final String UPDATE_CART = 
			"UPDATE `Carts` SET `ItemQuantity` =?, `ItemAmount`=? where `UserID`=? And `BakeryItemID`=?";
	private static final String GET_BAKERYITEM_BY_USERID = 
			"SELECT `ItemQuantity` FROM `Carts` WHERE `UserID`=? AND `BakeryItemID`=?";
	private static final String DELETE_BAKERYITEM_BY_USERID = 
			"DELETE FROM `Carts` WHERE `UserID`=? AND `BakeryItemID`=?";
	private static final String DELETE_CART = 
			"DELETE FROM `Carts` WHERE `UserID`=?";
	
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
					deleteItemFromCart(userID,itemID);
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
	
	private BakeryItem getItem(int bakeryItemID) throws Exception{
		BakeryItem bakeryItem = new BakeryItem(bakeryItemID);
		try(PreparedStatement ps = cartDao.getConnection().prepareStatement(VIEW_ITEM)){
			ps.setInt(1, bakeryItemID);
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				String imageURL = rs.getString("ImageURL");
				String description = rs.getString("Description");
				String itemName = rs.getString("ItemName");
				String itemSize = rs.getString("ItemSize");
				float price = Float.parseFloat(rs.getString("Price"));

				
				bakeryItem.setDescription(description);
				bakeryItem.setImageURL(imageURL);
				bakeryItem.setItemName(itemName);
				bakeryItem.setItemSize(itemSize);
				bakeryItem.setPrice(price);
			}
			
		}catch(Exception E) {
			E.printStackTrace();
			throw new Exception(E);
		}
		return bakeryItem;
	}
	
	// Get User Cart
	private Cart getShoppingCart(String userID) throws Exception {
		Cart cart = new Cart();
		try (PreparedStatement ps = cartDao.getConnection().prepareStatement(GET_CART);) {
			ps.setInt(1, Integer.parseInt(userID));
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	Integer itemId = rs.getInt("BakeryItemID");
            	Integer itemQuantity = rs.getInt("ItemQuantity");
            	BakeryItem bakeryItem = getItem(itemId);
            	CartItem cartItem = new CartItem(bakeryItem, itemQuantity);
            	cart.addItem(cartItem);
            }
		}
		catch (Exception e) {
			throw new Exception(e);
		}
		return cart;
	}
	
	// Delete Item from User Cart 
	public boolean deleteItemFromCart(String userID,String bakeryItemID) throws Exception{
		boolean isDeleted = false;
		try(PreparedStatement ps = cartDao.getConnection().prepareStatement(DELETE_BAKERYITEM_BY_USERID)){
			ps.setInt(1, Integer.parseInt(userID));
			ps.setInt(2, Integer.parseInt(bakeryItemID));
			ps.executeUpdate();
			isDeleted = true;	
		}
		catch (SQLException e) {
			isDeleted = false;
			throw new SQLException(e);
		}
		return isDeleted;
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
		public boolean addToCart(String userID, String bakeryItemID, String quantity) throws Exception{
			boolean isAdded = false;
			BakeryItem bakeryItem = getItem(Integer.parseInt(bakeryItemID));
			Integer itemQuantity = 0;
			
			try (PreparedStatement ps = cartDao.getConnection().prepareStatement(GET_BAKERYITEM_BY_USERID);) {
	            ps.setInt(1, Integer.parseInt(userID));
	            ps.setInt(2, Integer.parseInt(bakeryItemID));
	            System.out.println(ps);
	            ResultSet rs = ps.executeQuery();
	            while(rs.next()) {
	            	itemQuantity = rs.getInt("ItemQuantity");
	            }
	        } catch(SQLException e) {
	           e.printStackTrace();
	           throw new SQLException(e);
	        }
			
			if(itemQuantity > 0) {
				try(PreparedStatement ps = cartDao.getConnection().prepareStatement(UPDATE_CART)){
					ps.setInt(1, itemQuantity + Integer.parseInt(quantity));
					ps.setFloat(2, (itemQuantity + Integer.parseInt(quantity)) * bakeryItem.getPrice());
					ps.setInt(3, Integer.parseInt(userID));
					ps.setInt(4, Integer.parseInt(bakeryItemID));
					ps.executeUpdate();
					isAdded = true;
				} catch (SQLException e) {
					isAdded = false;
					throw new SQLException(e);
				}
			} else {
				try(PreparedStatement ps = cartDao.getConnection().prepareStatement(ADD_CART)){
					ps.setInt(1, Integer.parseInt(userID));
					ps.setInt(2, Integer.parseInt(bakeryItemID));
					ps.setInt(3, Integer.parseInt(quantity));
					ps.setFloat(4, bakeryItem.getPrice() * Integer.parseInt(quantity));
					ps.executeUpdate();
					isAdded = true;
				} catch (SQLException e) {
					isAdded = false;
					throw new SQLException(e);
				}
			}
			return isAdded;
		}
	
}
