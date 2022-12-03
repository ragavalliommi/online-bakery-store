package com.obs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbManagerImpl;
import com.obs.model.BakeryItem;

@WebServlet("/viewItem")
public class ViewController extends HttpServlet{

	/**
	 * 
	 */
	private DbManagerImpl viewDao = DbManagerImpl.getInstance();
	private static final long serialVersionUID = 1L;
	
	private BakeryItem getItemDetails(Integer bakeryItemID) throws Exception {
		BakeryItem bakeryItem  = viewDao.getItem(bakeryItemID);
		return bakeryItem;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException , IOException{
		
		try {
			if(request.getParameter("userID")!=null) {
				request.setAttribute("userID", request.getParameter("userID"));
			}
			else {
				request.setAttribute("userID", null);
			}
			if(request.getParameter("userName")!=null) {
				request.setAttribute("userName", request.getParameter("userName"));
			}
			else {
				request.setAttribute("userName", null);
			}
			if(request.getParameter("bakeryItemID")!=null) {
				request.setAttribute("bakeryItemID", request.getParameter("bakeryItemID"));
				String bakeryItemID = request.getParameter("bakeryItemID");
				BakeryItem item = getItemDetails(Integer.parseInt(bakeryItemID));
				request.setAttribute("item", item);
			}
			else {
				request.setAttribute("bakeryItemID", null);
			}
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ViewItemDetails.jsp");
			requestDispatcher.forward(request,response);
		}
		catch(Exception e){
			throw new ServletException(e);
		}
		
		
	}

}
