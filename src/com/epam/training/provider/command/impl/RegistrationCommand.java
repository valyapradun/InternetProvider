package com.epam.training.provider.command.impl;

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
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;

import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;
/**
 * Class for implementation of the command 'Registration of new user'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class RegistrationCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegistrationCommand.class.getName());
	
	private final UserService service;
	
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
				logger.log(Level.INFO, "User (id: " + user.getId() + ") has been registered.");
				HttpSession session = request.getSession();
				session.setAttribute(USER, user);
				request.setAttribute(REDIRECT_PARAMETER, "Yes");
			//	request.setAttribute(USER, user);
			//	page = USER_PAGE;
			//	page = request.getServletPath() + ACTION_SHOW_USER_PAGE;
				page = request.getServletPath() + ACTION_REGISTRATION;

			} catch (ServiceException e) {
				
				request.setAttribute(ERROR_MESSAGE, "It is impossible to registrate! ");
				logger.log(Level.ERROR, e);
				page = ERROR_PAGE;
				
			} catch (ValidateException e) {
				
				request.setAttribute(USER, user);
				request.setAttribute(ERROR_MESSAGE, ("Wrong data:! " + e.getMessage()).split("!"));
				logger.log(Level.WARN, e);
				page = REGISTRATION_PAGE;
				
			}

		} else {
			
			page = REGISTRATION_PAGE;
			
		}

		return page;
	}

}
