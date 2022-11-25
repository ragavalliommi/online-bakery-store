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

import org.junit.internal.JUnitSystem;

import com.obs.dao.DbConnector;
import com.obs.model.BakeryItem;


@WebServlet("/home")
public class HomePageController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	
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
		List<BakeryItem> bakeryItems =  userDao.getAllBakeryData();
		request.setAttribute("_items_data", bakeryItems);	
	}
	
	

}
