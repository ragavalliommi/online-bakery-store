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
import com.obs.model.CartItem;

public class DbConnector {
	private String jdbcURl = "jdbc:mysql://localhost:3306/obs?serverTimezone=UTC";
	private String jdbcUsername = "root";
	private String jdbcPassword = "onlineBakeryStore";
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
	private static final String SELECT_ALL_ITEMS =
			"SELECT `BakeryItemId`,`Description`,`ImageURL`,`ItemName`, `ItemSize`, `Price` FROM BakeryItems";
	
	private static final String GET_USER_BY_ID = 
			"SELECT * FROM `Users` WHERE `UserID`=? LIMIT 1";
	
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
	
	public User getUserByID(String userID) throws SQLException {
		User user = null;
		try (PreparedStatement ps = connection.prepareStatement(GET_USER_BY_ID);) {
            ps.setString(1, userID);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	String userName = rs.getString("UserName");
            	String phone = rs.getString("Phone");
                String email = rs.getString("Email");
                String deliveryAddress = rs.getString("DeliveryAddress");
                user = new User(userID, userName, phone, email, deliveryAddress);
            }
        } catch(SQLException e) {
           e.printStackTrace();
           throw new SQLException(e);
        }
		
		return user;
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
	
	/* Get Cart */
	public Cart getCart(String userID) throws Exception {
		Cart cart = new Cart();
		try (PreparedStatement ps = connection.prepareStatement(GET_CART);) {
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
        } catch(SQLException e) {
           e.printStackTrace();
           throw new SQLException(e);
        }
		return cart;
	}
	
	
	// Add To Cart
	public boolean addToCart(String userID, String bakeryItemID, String quantity) throws Exception{
		boolean isAdded = false;
		BakeryItem bakeryItem = getItem(Integer.parseInt(bakeryItemID));
		Integer itemQuantity = 0;
		
		try (PreparedStatement ps = connection.prepareStatement(GET_BAKERYITEM_BY_USERID);) {
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
			try(PreparedStatement ps = connection.prepareStatement(UPDATE_CART)){
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
			try(PreparedStatement ps = connection.prepareStatement(ADD_CART)){
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
	
	/* Delete Item from User Cart */
	public boolean deleteItemFromCart(String userID,String bakeryItemID) throws Exception{
		boolean isDeleted = false;
		try(PreparedStatement ps = connection.prepareStatement(DELETE_BAKERYITEM_BY_USERID)){
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
	
	
	/* Clear User Cart */
	public boolean clearUserCart(String userID) throws Exception{
		boolean isDeleted = false;
		try(PreparedStatement ps = connection.prepareStatement(DELETE_CART)){
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
	
	public List<BakeryItem> getAllBakeryData() throws SQLException {
		
		List<BakeryItem> itemsData = new ArrayList<BakeryItem>();
		
		try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ITEMS); ) {
			
			ResultSet executeQuery = preparedStatement.executeQuery();
			
			while(executeQuery.next()) {
				int bakeryItemId = executeQuery.getInt("BakeryItemID");
				String description = executeQuery.getString("Description");
				String imageURL = executeQuery.getString("ImageURL");
				String itemName = executeQuery.getString("ItemName");
				String itemSize = executeQuery.getString("ItemSize");
				
				
				float price = executeQuery.getFloat("Price");
				BakeryItem bakeryItem = new BakeryItem(bakeryItemId, description, imageURL, itemName, itemSize, price);
				itemsData.add(bakeryItem);
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
				
		return itemsData;
	}
	
	
	
	//order queries
		//save/insert order
		//fix DB for order
		//incorporate payment into order table
		
		private static final String ADD_ORDER = 
				"INSERT INTO `Orders` (userID, order_total) VALUES (?, ?);";
		
		private static final String ADD_ORDER_DETAIL = 
				"INSERT INTO `order_detail` (orderID, bakeryItemID, quantity, price) VALUES (?, ?, ?, ?);";
		
		private static final String GET_ORDER = 
				"SELECT order_id FROM `order` ORDER BY order_id DESC LIMIT 1";
		//get/read all orders
		
		//get/read one order
		
		// Method to insert order information to database.
	    public void createOrder(int userID, double totalPrice, List<CartItem> cartItems) {

	   
	        try(PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);) {
	            
	            
	            preparedStatement.setInt(1, userID);
	            preparedStatement.setDouble(2, totalPrice);
	            preparedStatement.executeUpdate();

	        } catch (Exception e) {
	            System.out.println("Create order catch:");
	            System.out.println(e.getMessage());
	        }

	        // Call create order detail method.
	        createOrderDetail(cartItems);
	    }
	    
	 // Method to get last order id in database.
	    public int getLastOrderId() {
	       
	        int orderId = 0;
	        try(PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDER);) {
	            
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if (resultSet.next()) {
	                orderId = resultSet.getInt(1);
	            }
	        } catch (Exception e) {
	            System.out.println(e.getMessage());
	        }
	        return orderId;
	    }
	    
		// Method to insert order detail information.
	    private void createOrderDetail(List<CartItem> cartItems) {
	     
	        // Get latest orderId to insert list of cartProduct to order.
	        int orderId = getLastOrderId();
	        
	        for (CartItem cartItem : cartItems) {
	           
	            try (PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER_DETAIL);){
	                
	                preparedStatement.setInt(1, orderId);
	                preparedStatement.setInt(2, cartItem.getBakeryItem().getBakeryItemId());
	                preparedStatement.setInt(3, cartItem.getItemQty());
	                preparedStatement.setDouble(4, cartItem.getBakeryItem().getPrice());
	                preparedStatement.executeUpdate();
	            } catch (Exception e) {
	                System.out.println("Create order_detail catch:");
	                System.out.println(e.getMessage());
	            }
	        }
	    }
}

	    
	