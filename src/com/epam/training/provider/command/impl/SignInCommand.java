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
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;
/**
 * Class for implementation of the command 'Sign In'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class SignInCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SignInCommand.class.getName());
	private Map<String, String> currentPage = new HashMap<>();
	private final static String UNSUCCESS_SIGNIN = "Such user or password doesn't exist! Try again!";
	private final static String UNSUCCESS = "It is impossible to sign in!";

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	public SignInCommand() {
		currentPage.put("admin", ACTION_SHOW_ADMIN_PAGE);
		currentPage.put("user", ACTION_SHOW_USER_PAGE);
	}

	
	/**
	 * Method for processing of action of the user - 'Sign In'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
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
				
				request.setAttribute(ERROR_MESSAGE, UNSUCCESS_SIGNIN);
				logger.log(Level.ERROR, "Unsuccessful attempt to sign in: such user or password doesn't exist!");
				page = ERROR_PAGE;
				
			} else {

				HttpSession session = request.getSession(true);
				session.setAttribute(USER, user);
				String role = user.getRole();
				logger.log(Level.INFO, "Successful attempt to sign in - session: " + session.getId() + ", user role: " + user.getRole() + ", user ID: " + user.getId());
						
				request.setAttribute(REDIRECT_PARAMETER, OK);
				page = request.getServletPath() + currentPage.get(role);
			}

		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			logger.log(Level.ERROR, "It is impossible to sign in!" + e);
			page = ERROR_PAGE;
			
		} 
		
		return page;
	}

}
