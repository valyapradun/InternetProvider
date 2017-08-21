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

/**
 * Class for implementation of the command 'End of the tariff of the user' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class EndTariffCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EndTariffCommand.class.getName());

	private final UserService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		int adminId = ((User) user).getId();
		int userId = Integer.parseInt(request.getParameter(USER_ID));
		
		try {
			
			service.endTariff(userId);
			logger.log(Level.INFO, "Admin (session id:" + adminId + ") has ended the tariff of the user (id user:  " + userId + ")");
			request.setAttribute(REDIRECT_PARAMETER, OK);
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			session.setAttribute(INFO_MESSAGE, "The tariff of the user " + userId + " had successfully ended!");
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to end the tariff of the user! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}
}
