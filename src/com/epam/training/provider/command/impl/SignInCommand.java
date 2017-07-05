package com.epam.training.provider.command.impl;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;

public class SignInCommand implements Command {
	private HashMap<String, String> pageUser = new HashMap<>();

	private UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	public SignInCommand() {
		pageUser.put("admin", ADMIN_PAGE);
		pageUser.put("user", USER_PAGE);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		String login = request.getParameter(USER_LOGIN);
		String password = request.getParameter(USER_PASSWORD);
		User user;
		try {
			user = service.authorize(login, password);
			if (user == null) {
				request.setAttribute(ERROR, "Such user or password doesn't exist! Try again!");
				page = ERROR_PAGE;
			} else {

			HttpSession session = request.getSession(true);
			session.setAttribute(USER, user);

			String role = user.getRole();
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = pageUser.get(role);
			}

		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to sign in!");
			page = ERROR_PAGE;
		}
		return page;
	}

}
