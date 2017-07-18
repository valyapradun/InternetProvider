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
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;

public class SearchTariffsCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SearchTariffsCommand.class.getName());
	private static final String LIST_TARIFFS = "tariffs";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Tariff> tariffs = null;
		
		String typeTariff = request.getParameter(TARIFF_TYPE);
		Map<String,String> parameters = new HashMap<String, String>();
		parameters.put(TARIFF_TYPE, typeTariff);
		
		try {
			
			tariffs = service.listTariffsWithParameters(parameters);
			logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") opened catalog of tariffs");
			request.setAttribute(LIST_TARIFFS, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			page = CATALOG_PAGE;
			
		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to display tariffs!");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
