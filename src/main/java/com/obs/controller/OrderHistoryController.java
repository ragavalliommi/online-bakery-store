package com.obs.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
import com.obs.model.Order;


/**
 * Servlet implementation class OrderHistoryController
 */
@WebServlet("/orderHistory")
public class OrderHistoryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String GET_ORDER_HISTORY = 
			"SELECT orderid, sum(amount) AS totalprice, orderdate, deliverymode FROM `Orders`"
			+ "where userid = ?"
			+ "group by orderid, orderdate, deliverymode;";
	
	private static final String GET_ORDER_DETAIL =
			"SELECT bakeryitemid, quantity FROM `Orders`"
					+ "where orderid = ? AND userid = ?;";
	
	private static final String VIEW_ITEM = 
			"SELECT * FROM `BakeryItems` WHERE  `BakeryItemID` = ? ";
	

       
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
					List<Order> ordersList =  getOrderHistory(userID);
					request.setAttribute("order_history", ordersList);	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderHistory.jsp");
		requestDispatcher.forward(request, response);
	}
	
	private List<Order> getOrderHistory(String userID) throws SQLException {
		List<Order> ordersList = new ArrayList<Order>();
		try(PreparedStatement ps = orderHistoryDao.getConnection().prepareStatement(GET_ORDER_HISTORY);){
			ps.setString(1, userID);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				
				int orderId = rs.getInt("orderid");
				float totalPrice = rs.getFloat("totalprice");
				String orderDate = rs.getString("orderdate");
				String deliveryMode = rs.getString("deliverymode");
				
				
				Order orderSummary = new Order(orderId, totalPrice, orderDate, deliveryMode);
				ordersList.add(orderSummary);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return ordersList;
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
					cart = getCart(userID, orderID);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				request.setAttribute("cart_data", cart.getCartItems());
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/OrderDetail.jsp");
				requestDispatcher.forward(request, response);

	}
	
	/* Get Cart */
	private Cart getCart(String userID, String orderID) throws Exception {
		Cart cart = new Cart();
		try (PreparedStatement ps = orderHistoryDao.getConnection().prepareStatement(GET_ORDER_DETAIL);) {
            ps.setInt(1, Integer.parseInt(orderID));
            ps.setInt(2, Integer.parseInt(userID));
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	Integer itemId = rs.getInt("bakeryitemid");
            	Integer itemQuantity = rs.getInt("quantity");
            	BakeryItem bakeryItem = getItem(itemId);
            	CartItem cartItem = new CartItem(bakeryItem, itemQuantity);
            	cart.addItem(cartItem);
            }
        } catch(SQLException e) {
           e.printStackTrace();
           throw new SQLException(e);
        }
		return cart;
	}
	
	private BakeryItem getItem(int bakeryItemID) throws Exception{
		BakeryItem bakeryItem = new BakeryItem(bakeryItemID);
		
		try(PreparedStatement ps = orderHistoryDao.getConnection().prepareStatement(VIEW_ITEM)){
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
	

}
