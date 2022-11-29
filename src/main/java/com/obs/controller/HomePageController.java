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


@WebServlet("/home")
public class HomePageController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SELECT_ALL_ITEMS =
			"SELECT `BakeryItemId`,`Description`,`ImageURL`,`ItemName`, `ItemSize`, `Price` FROM BakeryItems";

	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		try {

			retrieveAllItems(request, response);			
		} catch (Exception e) {
			throw new ServletException(e);
		}
		
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
		
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp");
		requestDispatcher.forward(request, response);
	}



	private void retrieveAllItems(HttpServletRequest request, HttpServletResponse response) throws SQLException {
		// TODO Auto-generated method stub
		
		DbConnector userDao = DbConnector.getInstance();
		List<BakeryItem> itemsData = new ArrayList<BakeryItem>();
		
		try (PreparedStatement preparedStatement = userDao.getConnection().prepareStatement(SELECT_ALL_ITEMS); ) {
			
			ResultSet executeQuery = preparedStatement.executeQuery();
			
			while(executeQuery.next()) {
				int bakeryItemId = executeQuery.getInt("BakeryItemID");
				String imageURL = executeQuery.getString("ImageURL");
				String itemName = executeQuery.getString("ItemName");
				float price = executeQuery.getFloat("Price");
				
				BakeryItem bakeryItem = new BakeryItem(bakeryItemId, imageURL, itemName, price);
				itemsData.add(bakeryItem);
			}
			
			
		} catch (SQLException e) {
			System.out.println(e);
		}
				
		
		request.setAttribute("_items_data", itemsData);	
	}
	
	

}
