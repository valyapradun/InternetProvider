package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

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
/**
 * Class for implementation of the command 'Show tariffs with criteria (type)'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ShowUserTariffsCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowUserTariffsCommand.class.getName());
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
		
		String action = request.getParameter(ACTION);

		String typeTariff = request.getParameter(TARIFF_TYPE);
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put(TARIFF_TYPE, typeTariff);

		try {

			tariffs = service.listTariffsWithCriteria(criteria);
			logger.log(Level.INFO, "User (id session: " + request.getSession(false).getId() + ") displayed tarifs.");
			request.setAttribute(LIST_TARIFFS, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			request.setAttribute(ACTION, action);
			page = USER_TARIFFS_PAGE;
			

			HttpSession session = request.getSession(false);
			request.setAttribute(INFO_MESSAGE, session.getAttribute(INFO_MESSAGE));
			session.setAttribute(INFO_MESSAGE, null);

		} catch (ServiceException | ValidateException e) {

			request.setAttribute(ERROR_MESSAGE, "It is impossible to display tariffs!");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;

		}
		return page;
	}

}
