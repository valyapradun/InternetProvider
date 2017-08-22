package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

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

/**
 * Class for implementation of the command 'Put Ban for users who have negative balance'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class PutBanCommand implements Command {
	private final static Logger logger = LogManager.getLogger(PutBanCommand.class.getName());
	private final static String UNSUCCESS = "It is impossible to put the ban!";
	private final static String SUCCESS = "The bans had successfully added!";

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
				
	}

	
	/**
	 * Method for processing of action of the administrator - 'Put Ban for users who have negative balance'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		int adminId = ((User) user).getId();
		
		try {
			service.putBan(adminId);
		
			request.setAttribute(REDIRECT_PARAMETER, OK);
			session.setAttribute(INFO_MESSAGE, SUCCESS);
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			logger.log(Level.INFO, "Admin (session id:" + request.getSession(false).getId() + ") has put the ban");
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			page = ERROR_PAGE;
			logger.log(Level.ERROR, e);
			
		}
		return page;
	}

}
