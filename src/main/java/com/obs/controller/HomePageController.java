package com.obs.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManagerImpl;
import com.obs.model.BakeryItem;


@WebServlet("/home")
public class HomePageController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private DbManagerImpl homePageDao = DbManagerImpl.getInstance();

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			
			List<BakeryItem> bakeryItems =  retrieveAllBakeryItems();
			request.setAttribute("_items_data", bakeryItems);	

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
	
	
	
	
	private List<BakeryItem> retrieveAllBakeryItems() throws SQLException {
		// TODO Auto-generated method stub
		
		List<BakeryItem> bakeryItems =  homePageDao.getAllBakeryData();
		return bakeryItems;
	}
	
	

}
