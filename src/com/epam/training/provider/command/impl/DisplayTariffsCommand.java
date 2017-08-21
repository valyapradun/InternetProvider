package com.epam.training.provider.command.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;
/**
 * Class for implementation of the command 'Display tariffs' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class DisplayTariffsCommand implements Command {
	private final static Logger logger = LogManager.getLogger(DisplayTariffsCommand.class.getName());
	private static final String LIST_TARIFFS = "tariffs";
	private final static String UNSUCCESS = "It is impossible to display tariffs!";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	
	/**
	 * Method for processing of action of the administrator - 'Display tariffs'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Tariff> tariffs = null;
		
		String typeTariff = request.getParameter(TARIFF_TYPE);
		
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put(TARIFF_TYPE, typeTariff);
		
		String action = request.getParameter(ACTION);
		
		try {
			
			tariffs = service.listTariffsWithCriteria(criteria);
			request.setAttribute(LIST_TARIFFS, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			request.setAttribute(ACTION, action);
			
			HttpSession session = request.getSession(false);
			request.setAttribute(INFO_MESSAGE, session.getAttribute(INFO_MESSAGE));
			session.setAttribute(INFO_MESSAGE, null);
			
			page = LIST_TARIFFS_PAGE;
			logger.log(Level.INFO, "Administrator (id session: " + request.getSession(false).getId() + ") displayed tarifs.");
		
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}
}
