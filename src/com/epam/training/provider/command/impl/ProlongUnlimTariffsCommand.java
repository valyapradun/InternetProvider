package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'To prolong unlim tariffs which have been bought more than 30 days ago'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ProlongUnlimTariffsCommand implements Command{
	private final static Logger logger = LogManager.getLogger(ProlongUnlimTariffsCommand.class.getName());
	
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		try {
			service.prolongUnlimTariffs();
			logger.log(Level.INFO, "Admin (session id:" + request.getSession(false).getId() + ") has prolonged unlim tariffs");
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			request.getSession(false).setAttribute(INFO_MESSAGE, "The unlim tariffs had successfully prolonged!");
			
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "Prolonging unlim tariffs wasn't executed! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
		}
		
		return page;
	}
}

