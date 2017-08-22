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
	private final static String UNSUCCESS = "Prolonging unlim tariffs wasn't executed! ";
	private final static String SUCCESS = "The unlim tariffs had successfully prolonged!";
	
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	/**
	 * Method for processing of action of the administrator - 'To prolong unlim tariffs which have been bought more than 30 days ago'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		try {
			service.prolongUnlimTariffs();
			
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, SUCCESS);
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			logger.log(Level.INFO, "Admin (session id:" + request.getSession(false).getId() + ") has prolonged unlim tariffs");
			
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			page = ERROR_PAGE;
			logger.log(Level.ERROR, e);
		}
		
		return page;
	}
}

