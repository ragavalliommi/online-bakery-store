package com.obs.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.BakeryItem;

@WebServlet("/item")
public class ViewController extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4917965091904591241L;
	
	private BakeryItem getItemDetails(int bakeryItemID) throws Exception {
		DbConnector db = DbConnector.getInstance();
		BakeryItem item;
		try {
			 item = db.getItem(bakeryItemID);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e);
		}
		return item;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
		
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
			
			int bakeryItemID = Integer.parseInt(request.getParameter("bakeryItemID"));
			BakeryItem item = getItemDetails(bakeryItemID);
			request.setAttribute("bakeryItem", item);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/ViewItemDetails.jsp");
			requestDispatcher.forward(request,response);
		}
		catch(Exception e){
			throw new ServletException(e);
		}
		
		
	}

}