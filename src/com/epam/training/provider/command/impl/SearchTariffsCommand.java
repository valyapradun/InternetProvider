package com.epam.training.provider.command.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * Class for implementation of the command 'Search all tariffs' by user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class SearchTariffsCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SearchTariffsCommand.class.getName());
	private static final String LIST_TARIFFS = "tariffs";
	private final static String UNSUCCESS = "It is impossible to display tariffs!";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	
	/**
	 * Method for processing of action of the user - 'Search all tariffs'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Tariff> tariffs = null;
		
		String typeTariff = request.getParameter(TARIFF_TYPE);
		Map<String,String> criteria = new HashMap<String, String>();
		criteria.put(TARIFF_TYPE, typeTariff);
		
		try {
			
			tariffs = service.listTariffsWithCriteria(criteria);
			logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") opened catalog of tariffs");
			request.setAttribute(LIST_TARIFFS, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			page = CATALOG_PAGE;
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
