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
import com.obs.model.Order;

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
	private static final String VIEW_ITEM = 
			"SELECT * FROM `BakeryItems` WHERE  `BakeryItemID` = ? ";	
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
	
	
	
	//order queries
		//save/insert order
		//fix DB for order
		//incorporate payment into order table
		
		public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}



		private static final String ADD_ORDER = 
				"INSERT INTO `Orders` (orderID, userID, bakeryItemID, quantity, amount, deliverymode) VALUES (?, ?, ?, ?, ?, ?);";
		
		private static final String GET_ORDER_NUMBER = 
				"SELECT MAX(entryID) as entryID FROM `Orders`;";
		
		private static final String GET_ORDER_HISTORY = 
				"SELECT orderid, sum(amount) AS totalprice, orderdate, deliverymode FROM `Orders`"
				+ "where userid = ?"
				+ "group by orderid, orderdate, deliverymode;";
		
		
		public int getOrderNumber() throws SQLException{
			int maxEntryID = 0;
			
			try(PreparedStatement ps = connection.prepareStatement(GET_ORDER_NUMBER);){
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					maxEntryID = rs.getInt("entryID");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return maxEntryID;
		}

		
		public boolean insertOrder(Order order) throws SQLException{
			boolean isOrderInserted = false;
			
			try(PreparedStatement ps = connection.prepareStatement(ADD_ORDER);){
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
		
		public List<Order> getOrderHistory(String userID) throws SQLException {
			List<Order> ordersList = new ArrayList<Order>();
			try(PreparedStatement ps = connection.prepareStatement(GET_ORDER_HISTORY);){
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
		
		
		private static final String GET_ORDER_DETAIL =
				"SELECT bakeryitemid, quantity FROM `Orders`"
						+ "where orderid = ? AND userid = ?;";

				
		/* Get Cart */
		public Cart getCart(String userID, String orderID) throws Exception {
			Cart cart = new Cart();
			try (PreparedStatement ps = connection.prepareStatement(GET_ORDER_DETAIL);) {
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
		
		

}

	    
	