package com.obs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.obs.dao.DbConnector;
import com.obs.model.User;

/**
 * Servlet implementation class InsertUser
 */
@WebServlet("/register")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private DbConnector userDao = DbConnector.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/UserRegister.jsp");
		requestDispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String deliveryAddress = request.getParameter("deliveryAddress");
		User user = new User(email);
		user.setUserName(userName);
		user.setPhone(phone);
		user.setPassword(password);
		user.setDeliveryAddress(deliveryAddress);
		
		try {
			userDao.registerUser(user);
			
		}
		catch(Exception e) {
			System.out.println(e);
		}
		RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/views/SuccessMessage.jsp");
		requestDispatcher.forward(request, response);
	}

}
