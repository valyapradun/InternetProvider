package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'Delete the user' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class DeleteUserCommand implements Command {
	private final static Logger logger = LogManager.getLogger(DeleteUserCommand.class.getName());
	private final static String SUCCESS = "The user had successfully deleted - id: ";

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
				
	}

	
	/**
	 * Method for processing of action of the administrator - 'Delete the user'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		int id = Integer.parseInt(request.getParameter(USER_ID));
		
		try {
			
			service.deleteUser(id);
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, SUCCESS + id);
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			logger.log(Level.INFO, "User (id: " + id + ") has been deleted by the admin (session id:" + request.getSession(false).getId() + ")");
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to delete the user!");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
