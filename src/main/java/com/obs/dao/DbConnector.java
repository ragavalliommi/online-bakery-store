package com.obs.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.obs.model.BakeryItem;
import com.obs.model.User;
import com.obs.model.Cart;

public class DbConnector {
	private String jdbcURl = "jdbc:mysql://localhost:3306/obs?serverTimezone=UTC";
	private String jdbcUsername = "root";
	private String jdbcPassword = "Seabreeze14303";
	private Connection connection = null; // single instance of Connection
	private static DbConnector userDao = new DbConnector();
	private static final String INSERT_USER =
			"INSERT INTO `Users`(`UserName`, `Email`,`Phone`, `Password`,`DeliveryAddress`) VALUES (? ,? ,?, ?, ?);";
	private static final String GET_USER = 
			"SELECT `UserID`,`UserName`,`Password` FROM `Users` WHERE `Email`=? AND `Password` =? LIMIT 1";
	private static final String SEARCH_ITEMS = 
			"SELECT `BakeryItemID`, `ItemName`, `ItemSize`, `Price`, `ImageURL` FROM `BakeryItems` WHERE  `ItemName` LIKE ? ";
	private static final String GET_ALL_ITEMS = 
			"SELECT `BakeryItemID`, `ItemName`, `ItemSize`, `Price`, `ImageURL` FROM `BakeryItems` WHERE  `ItemName` LIKE ? ";
	private static final String VIEW_ITEM = 
			"SELECT * FROM `BakeryItems` WHERE  `BakeryItemID` = ? ";
	private static final String ADD_CART = 
			"INSERT INTO `Carts`(`UserID`, `BakeryItemID`, `ItemQuantity`, `ItemAmount`, `Status`) values (?, ?, ?, ?, 'P')";
	
	public DbConnector() {
		establishDatabaseConnection();
    }
	
	public static DbConnector getInstance() {
		return userDao;
	}

	private void establishDatabaseConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURl, jdbcUsername, jdbcPassword);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean registerUser(User user) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");

			Connection connection = DriverManager
		
				.getConnection(jdbcURl, jdbcUsername, jdbcPassword);

				// Step 2:Create a statement using connection object
			try(PreparedStatement preparedStatement = connection
						.prepareStatement(INSERT_USER);) {
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPhone());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getDeliveryAddress());
			
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
			
			
		} catch (SQLException e) {
			System.out.println(e);
            return false;
		}
		return true;
	}
	
	public User getUser(String email, String pwd) throws SQLException {
		User existingUser = new User(email);
		try (PreparedStatement ps = connection.prepareStatement(GET_USER);) {
            ps.setString(1, existingUser.getEmail());
            ps.setString(2, pwd);
            //ps.setString(0, pwd);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String userId = rs.getString("UserID");
                String password = rs.getString("Password");
                String userName = rs.getString("UserName");
                existingUser.setPassword(password);   
                if(userId != null && password != null) {
                	if(existingUser.verify(pwd)) {
                		System.out.println("verified");
                		existingUser.setUserID(userId);
                		existingUser.setUserName(userName);
                	}
                }
            }
        } catch(SQLException e) {
           e.printStackTrace();
           throw new SQLException(e);
        }
		
		return existingUser;
	}
	
	public List<BakeryItem> getItems(String searchString) throws Exception{
		List<BakeryItem> items = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(SEARCH_ITEMS)){
			ps.setString(1, "%"+searchString+"%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int itemId = Integer.parseInt(rs.getString("BakeryItemID"));
				String imageURL = rs.getString("ImageURL");
				String itemName = rs.getString("ItemName");
				String itemSize = rs.getString("ItemSize");
				float price = Float.parseFloat(rs.getString("Price"));

				
				items.add(new BakeryItem(itemId, imageURL,itemName, itemSize, price));
			}
			
		}catch(Exception E) {
			E.printStackTrace();
			throw new Exception(E);
		}
		return items;
	}
	
	public List<BakeryItem> getAllItems() throws Exception{
		List<BakeryItem> items = new ArrayList<>();
		
		try(PreparedStatement ps = connection.prepareStatement(GET_ALL_ITEMS)){
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int itemId = Integer.parseInt(rs.getString("BakeryItemID"));
				String imageURL = rs.getString("ImageURL");
				String itemName = rs.getString("ItemName");
				String itemSize = rs.getString("ItemSize");
				float price = Float.parseFloat(rs.getString("Price"));

				
				items.add(new BakeryItem(itemId, imageURL,itemName, itemSize, price));
			}
			
		}catch(Exception E) {
			E.printStackTrace();
			throw new Exception(E);
		}
		return items;
	}
	
	public BakeryItem getItem(int bakeryItemID) throws Exception{
		BakeryItem bakeryItem = new BakeryItem(bakeryItemID);
		
		try(PreparedStatement ps = connection.prepareStatement(VIEW_ITEM)){
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
	
	public boolean addToCart(Integer userID, Cart cart) throws Exception{
		    boolean isAdded = false;

			try(PreparedStatement ps = connection.prepareStatement(ADD_CART)){
				for (BakeryItem b: cart.getItemList()) {
					ps.setInt(1, userID);
					ps.setInt(2, b.getBakeryItemId());
					ps.setInt(3, cart.getItemQty());
					ps.setFloat(4, b.getPrice());
					ps.executeUpdate();
				}
				isAdded = true;
			} catch (SQLException e) {
				isAdded = false;
				throw new SQLException(e);
		}
		return isAdded;
	}
	
}