package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;

import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;

public class RegCommand implements Command {
	private UserService service;
	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		if (request.getMethod().equals("POST")) {
			String login = request.getParameter(USER_LOGIN);
			String password = request.getParameter(USER_PASSWORD);

			String name = request.getParameter(USER_NAME);
			byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
			name = new String(bytes, StandardCharsets.UTF_8);

			String email = request.getParameter(USER_EMAIL);

			User user = new User();
			user.setLogin(login);
			user.setPassword(password);
			user.setName(name);
			user.setEmail(email);

			try {
				service.registration(user);
				HttpSession session = request.getSession();
				session.setAttribute(USER, user);
				request.setAttribute(REDIRECT_PARAMETER, "Yes");
				request.setAttribute(USER, user);
				page = USER_PAGE;

			} catch (ServiceException e) {
				request.setAttribute(ERROR, "It is impossible to registrate! " + e.getMessage());
				page = ERROR_PAGE;
			} catch (ValidateException e) {
				request.setAttribute(USER, user);
				request.setAttribute(ERROR, ("Wrong data:! " + e.getMessage()).split("!"));
				page = REGISTRATION_PAGE;
			}

		} else {
			page = REGISTRATION_PAGE;
		}

		return page;
	}

}
