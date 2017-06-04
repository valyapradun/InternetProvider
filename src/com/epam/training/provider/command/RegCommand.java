package com.epam.training.provider.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.UserServiceException;
import com.epam.training.provider.service.impl.UserServiceImpl;

public class RegCommand implements Command{
	private UserService service;
	
	{
		service = new UserServiceImpl();
	}
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		User user = new User();
		String login = request.getParameter("login");
		user.setLogin(login);
		String password = request.getParameter("password");
		user.setPassword(password);
		String name = request.getParameter("name");
		user.setName(name);
		String email = request.getParameter("email");
		user.setEmail(email);
		
		boolean resultReg;
		try {
			resultReg = service.registration(user);
		
		if (resultReg) {
			System.out.println(user);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			page = "/user_main.jsp";
		} else {
			page = "/error.jsp";
		}
		} catch (UserServiceException e) {
			page = "/error.jsp";
		}
		return page;
	}

}
