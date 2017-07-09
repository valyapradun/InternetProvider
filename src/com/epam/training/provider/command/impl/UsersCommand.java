package com.epam.training.provider.command.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
//import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;

public class UsersCommand implements Command {
	private static final String RESULT = "users";

	private UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<User> users = null;
	//	String action = request.getParameter(ACTION);
		try {
			users = service.listUsersWithParameters();
			request.setAttribute(RESULT, users);
	//		request.setAttribute(ACTION, action);
			page = ADMIN_USERS;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to display users! " + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;
	}

}
