package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.epam.training.provider.util.Permanent.*;



import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class UserMainCommand implements Command{
	private UserService service;
	
	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}
	
	

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		User user = null;
		String action = request.getParameter(ACTION);
		HttpSession session = request.getSession(false);
		Object user2 = session.getAttribute(USER);
		
		String login =((User)user2).getLogin();
				
	//	String login = request.getParameter("login");
		try {
			user = service.userByLogin(login);
			request.setAttribute("user", user);
			request.setAttribute(ACTION, action);
			page = USER_PAGE;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to display user! " + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;

	}

}
