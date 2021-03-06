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
 * Class for implementation of the command 'Remove Ban for user who has paid'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class RemoveBanCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RemoveBanCommand.class.getName());
	private final static String UNSUCCESS = "It is impossible to remove the ban!";
	private final static String SUCCESS = "The ban had successfully removed!";

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
				
	}

	
	/**
	 * Method for processing of action of the administrator - 'Remove Ban for user who has paid'.
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
		int userId = Integer.parseInt(request.getParameter(USER_ID));
		
		try {
			service.removeBan(adminId, userId);
			
			logger.log(Level.INFO, "Admin (session id:" + request.getSession(false).getId() + ") has removed the ban");
			request.setAttribute(REDIRECT_PARAMETER, OK);
			session.setAttribute(INFO_MESSAGE, SUCCESS);
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
