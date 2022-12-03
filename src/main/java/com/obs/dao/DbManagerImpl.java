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

public class DbManagerImpl implements DbManager {
	private String jdbcURl = "jdbc:mysql://localhost:3306/obs?serverTimezone=UTC";
	private String jdbcUsername = "root";
	private String jdbcPassword = "onlineBakeryStore";
	
	
	private Connection connection = null; // single instance of Connection
	
	private static DbManagerImpl dbMgr = new DbManagerImpl();
	
	private static final String INSERT_USER =
			"INSERT INTO `Users`(`UserName`, `Email`,`Phone`, `Password`,`DeliveryAddress`) VALUES (? ,? ,?, ?, ?);";
	private static final String GET_USER = 
			"SELECT `UserID`,`UserName`,`Password` FROM `Users` WHERE `Email`=? AND `Password` =? LIMIT 1";
	private static final String SELECT_ALL_ITEMS =
			"SELECT `BakeryItemId`,`Description`,`ImageURL`,`ItemName`, `ItemSize`, `Price` FROM BakeryItems";
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
	private static final String SEARCH_ITEMS = 
			"SELECT `BakeryItemID`, `ItemName`, `ItemSize`, `Price`, `ImageURL` FROM `BakeryItems` WHERE  `ItemName` LIKE ? ";
	
	
	public DbManagerImpl() {
		establishDatabaseConnection();
    }
	
	public static DbManagerImpl getInstance() {
		return dbMgr;
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
	
	// DB connection getter and setter
	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public boolean registerUser(String userName,String phone, String email, String password , String deliveryAddress) throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		User user;
		user = new User()
				.setEmail(email)
				.setUserName(userName)
				.setPhone(phone)
				.setPassword(password)
				.setDeliveryAddress(deliveryAddress);
//			User user = new User(email);
//			user.setUserName(userName);
//			user.setPhone(phone);
//			user.setPassword(password);
//			user.setDeliveryAddress(deliveryAddress);
			
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
			if(preparedStatement.executeUpdate()<=0) {
				return false;
			}
			
			
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
	
	public List<BakeryItem> searchItems(String searchString) throws Exception{
		List<BakeryItem> items = new ArrayList<>();
		try(PreparedStatement ps = connection.prepareStatement(SEARCH_ITEMS)){
			ps.setString(1, "%"+searchString+"%");
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				int itemId = Integer.parseInt(rs.getString("BakeryItemID"));
				String imageURL = rs.getString("ImageURL");
				String itemName = rs.getString("ItemName");
				float price = Float.parseFloat(rs.getString("Price"));

				
				items.add(new BakeryItem(itemId, imageURL,itemName, price));
			}
			
		}catch(Exception E) {
			E.printStackTrace();
			throw new Exception(E);
		}
		return items;
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
			if(userID != null) {
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
		CartItem cartItem = new CartItem(bakeryItem, Integer.parseInt(quantity));
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
				ps.setInt(1, itemQuantity + cartItem.getItemQty());
				ps.setFloat(2, (itemQuantity + cartItem.getItemQty()) * bakeryItem.getPrice());
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
				ps.setInt(3, cartItem.getItemQty());
				ps.setFloat(4, bakeryItem.getPrice() * cartItem.getItemQty());
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
			if(userID != null) {
				ps.setInt(1, Integer.parseInt(userID));
				ps.executeUpdate();
				isDeleted = true;	
			}
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
		




		private static final String ADD_ORDER = 
				"INSERT INTO `Orders` (orderID, userID, bakeryItemID, paymentID, quantity, amount, deliverymode) VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		private static final String GET_ORDER_NUMBER = 
				"SELECT MAX(entryID) as entryID FROM `Orders`;";
		
		private static final String GET_ORDER_HISTORY = 
				"SELECT orderid, sum(amount) AS totalprice, orderdate, deliverymode FROM `Orders`"
				+ "where userid = ?"
				+ "group by orderid, orderdate, deliverymode;";
		
		private static final String GET_PAYMENT_ID = 
				"SELECT paymentID FROM `Payments` WHERE paymentmode = ?";
		
		
		public void placeOrder(String userID, String paymentMode, String deliveryMode) throws Exception {
			
			Cart cart = getCart(userID);
			ArrayList<CartItem> cartList = cart.getCartItems();
			int id = getOrderNumber() + 1;
			int paymentID = getPaymentID(paymentMode);
			insertAllItemsInOrder(cartList, id, userID, paymentID, deliveryMode);
			boolean cartCleared = clearUserCart(userID);
			if(!cartCleared) System.out.println("cart not cleared");
			
		}
		
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
				ps.setInt(4, order.getPaymentId());
				ps.setInt(5, order.getQuantity());
				ps.setFloat(6, order.getAmount());
				ps.setString(7, order.getDeliveryMode());
				System.out.println(ps);
				ps.executeUpdate();
				isOrderInserted = true;	
			}
			return isOrderInserted;
		}
		
		public void insertAllItemsInOrder(ArrayList<CartItem> cartList, int id, String userID, int paymentID, String deliveryMode) throws SQLException {
			boolean orderInserted = false;
			for(CartItem c : cartList) {
				Order order = new Order();
				order.setOrderId(id);
				order.setUserId(userID);
				order.setBakeryItemId(c.getBakeryItem().getBakeryItemId());
				order.setPaymentId(paymentID);
				order.setQuantity(c.getItemQty());
				order.setAmount(c.getItemQty() * c.getBakeryItem().getPrice());
				order.setDeliveryMode(deliveryMode);

				orderInserted = insertOrder(order);
				if(!orderInserted) break;
				
			}
		}
		
		public List<Order> getOrderHistory(String userID) throws SQLException {
			List<Order> ordersList = new ArrayList<Order>();
			try(PreparedStatement ps = connection.prepareStatement(GET_ORDER_HISTORY);){
				if(userID != null) {
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
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return ordersList;
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
		
		
		private static final String GET_ORDER_DETAIL =
				"SELECT bakeryitemid, quantity FROM `Orders`"
						+ "where orderid = ? AND userid = ?;";

				
		/* Get Cart */
		public Cart getOrder(String userID, String orderID) throws Exception {
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
		
		public int getPaymentID(String paymentMode) throws SQLException{
			int paymentID = 0;
			
			try(PreparedStatement ps = connection.prepareStatement(GET_PAYMENT_ID);){
				ps.setString(1, paymentMode);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					paymentID = rs.getInt("paymentID");
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return paymentID;
		}
		
		
		
		

}

	    
	