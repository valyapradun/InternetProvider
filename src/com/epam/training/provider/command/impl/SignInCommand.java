package com.epam.training.provider.command.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;

public class SignInCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SignInCommand.class.getName());
	private Map<String, String> currentPage = new HashMap<>();

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	public SignInCommand() {
		currentPage.put("admin", ACTION_SHOW_ADMIN_PAGE);
		currentPage.put("user", ACTION_SHOW_USER_PAGE);
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		User user;
		
		String login = request.getParameter(USER_LOGIN);
		String password = request.getParameter(USER_PASSWORD);
		
		try {
			user = service.authorize(login, password);
			logger.log(Level.INFO, "Attempt to sign in (login: " + login + ")");
			
			
			if (user == null) {
				
				request.setAttribute(ERROR_MESSAGE, "Such user or password doesn't exist! Try again!");
				logger.log(Level.ERROR, "Unsuccessful attempt to sign in: such user or password doesn't exist!");
				page = ERROR_PAGE;
				
			} else {

				HttpSession session = request.getSession(true);
				session.setAttribute(USER, user);
				String role = user.getRole();
				logger.log(Level.INFO, "Successful attempt to sign in - session: " + session.getId() + ", user role: " + user.getRole() + ", user ID: " + user.getId());
						
				request.setAttribute(REDIRECT_PARAMETER, "Yes");
				page = request.getServletPath() + currentPage.get(role);
			}

		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to sign in!");
			logger.log(Level.ERROR, "It is impossible to sign in!" + e);
			page = ERROR_PAGE;
			
		}
		
		return page;
	}

}
