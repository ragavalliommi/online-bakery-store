package com.obs.controller;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManagerImpl;
import com.obs.model.BakeryItem;

@WebServlet("/search")
public class SearchController extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DbManagerImpl searchDao = DbManagerImpl.getInstance();
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException {
		try {
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
			String searchString = request.getParameter("searchString");
			
			if(searchString !=null) {
				List<BakeryItem> list = searchItems(searchString);
				request.setAttribute("_items_data", list);
			}
			else {
				request.setAttribute("_items_data", null);
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/HomePage.jsp");
			requestDispatcher.forward(request, response);
			
		}catch(Exception e) {
			throw new ServletException(e);
		}
		
	}
	private List<BakeryItem> searchItems(String searchString) throws Exception{
		
		
		List<BakeryItem> items = new ArrayList<>();
		items = searchDao.searchItems(searchString);
		return items;
	}

}
