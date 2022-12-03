package com.obs.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.obs.model.BakeryItem;
import com.obs.model.Cart;
import com.obs.model.CartItem;
import com.obs.model.Order;
import com.obs.model.User;

public interface DbManager {
	public Connection getConnection();
	public void setConnection(Connection connection);
	public boolean registerUser(String userName,String phone, String email, String password , String deliveryAddress) throws Exception;
	public User getUser(String email, String pwd) throws SQLException ;
	public List<BakeryItem> searchItems(String searchString) throws Exception;
	public User getUserByID(String userID) throws SQLException;
	public BakeryItem getItem(int bakeryItemID) throws Exception;
	public Cart getCart(String userID) throws Exception ;
	public boolean addToCart(String userID, String bakeryItemID, String quantity) throws Exception;
	public boolean deleteItemFromCart(String userID,String bakeryItemID) throws Exception;
	public boolean clearUserCart(String userID) throws Exception;
	public void placeOrder(String userID, String paymentMode, String deliveryMode) throws Exception;
	public int getOrderNumber() throws SQLException;
	public boolean insertOrder(Order order) throws SQLException;
	public void insertAllItemsInOrder(ArrayList<CartItem> cartList, int id, String userID, int paymentID, String deliveryMode) throws SQLException;
	public List<Order> getOrderHistory(String userID) throws SQLException;
	public List<BakeryItem> getAllBakeryData() throws SQLException;
	public Cart getOrder(String userID, String orderID) throws Exception;
	public int getPaymentID(String paymentMode) throws SQLException;
	
}
