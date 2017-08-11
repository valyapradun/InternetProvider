package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.training.provider.util.Permanent.*;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'To show user page after authentication'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ShowUserPageCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowUserPageCommand.class.getName());
	
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
		int id = ((User) currentUser).getId();

		try {
			
			user = service.userById(id);
			logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") opened page of user.");
			request.setAttribute(USER, user);
			request.setAttribute(ACTION, action);
			page = USER_PAGE;
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to display user! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;

	}

}
