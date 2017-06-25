package com.epam.training.provider.command.impl;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;


public class SignInCommand implements Command{
	private UserService service;
	
	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		User user;
		try {
			user = service.authorize(login, password);
		
		if (user != null) {
			
			
			HttpSession session = request.getSession(true);
			System.out.println("1 session: " + session);
			session.setAttribute("user", user);
			System.out.println("user authentication: " + user);
			
			
			page = "/user_main.jsp";
			
			if (user.getRole().equals("admin")) {
				page = "/admin_index.jsp";
				
			/*	try {
					response.sendRedirect(request.getContextPath() + page);
					
				} catch (IOException e) {
					page = "/error.jsp";
				}*/
			}
		} else {
			page = "/error.jsp";
		}
		} catch (ServiceException e) {
			page = "/error.jsp";
		}
		return page;
		
		// Вытащить бин из сессии
	//	User loggedUser = (User) request.getSession().getAttribute("LOGGED_USER");
	//	if (loggedUser == null) {
	//	  response.sendRedirect("/login.jsp");
	//	}
		
		
		
	}

}
