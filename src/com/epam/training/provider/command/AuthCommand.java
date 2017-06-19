package com.epam.training.provider.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;


public class AuthCommand implements Command{
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
			System.out.println(user);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			page = "/user_main.jsp";
			
			if (user.getRole().equals("admin")) {
				page = "/admin_main.jsp";
			}
		} else {
			page = "/error.jsp";
		}
		} catch (ServiceException e) {
			page = "/error.jsp";
		}
		return page;
	}

}
