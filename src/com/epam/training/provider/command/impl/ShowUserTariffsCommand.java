package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class ShowUserTariffsCommand implements Command {
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
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put(TARIFF_TYPE, typeTariff);

		try {

			tariffs = service.listTariffsWithParameters(parameters);
			request.setAttribute(LIST_TARIFFS, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			request.setAttribute(ACTION, action);
			page = USER_TARIFFS_PAGE;

		} catch (ServiceException e) {

			request.setAttribute(ERROR_MESSAGE, "It is impossible to display tariffs!" + e.getMessage());
			page = ERROR_PAGE;

		}
		return page;
	}

}
