package com.epam.training.provider.command.impl;

import java.util.List;

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
//import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;
/**
 * Class for implementation of the command 'Display users' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class DisplayUsersCommand implements Command {
	private static final String LIST_USERS = "users";
	private final static Logger logger = LogManager.getLogger(DisplayUsersCommand.class.getName());
	private final static String UNSUCCESS = "It is impossible to display users! ";
			
	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	
	/**
	 * Method for processing of action of the administrator - 'Display users'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<User> users = null;
		
		String action = request.getParameter(ACTION);
		
		try {
			
			users = service.listUsersWithParameters();
			request.setAttribute(LIST_USERS, users);
			request.setAttribute(ACTION, action);

			HttpSession session = request.getSession(false);
			request.setAttribute(INFO_MESSAGE, session.getAttribute(INFO_MESSAGE));
			session.setAttribute(INFO_MESSAGE, null);
			
			page = LIST_USERS_PAGE;
			logger.log(Level.INFO, "Administrator (id session: " + request.getSession(false).getId() + ") displayed users.");
		
		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
