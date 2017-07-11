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

public class ShowUserPageCommand implements Command {
	private final UserService service;

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
		Object currentUser = session.getAttribute(USER);
		String login = ((User) currentUser).getLogin();

		try {
			user = service.userByLogin(login);
			request.setAttribute(USER, user);
			request.setAttribute(ACTION, action);
			page = USER_PAGE;
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "It is impossible to display user! " + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;

	}

}
