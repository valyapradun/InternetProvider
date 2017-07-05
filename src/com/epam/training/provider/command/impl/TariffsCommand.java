package com.epam.training.provider.command.impl;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;
import static com.epam.training.provider.util.Permanent.*;

public class TariffsCommand implements Command {
	private static final String KEY = "type";
	private static final String RESULT = "tariffs";

	private TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Tariff> tariffs = null;
		String typeTariff = request.getParameter(TARIFF_TYPE);
		String action = request.getParameter(ACTION);
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY, typeTariff);
		try {
			tariffs = service.listTariffsWithParameters(parameters);
			request.setAttribute(RESULT, tariffs);
			request.setAttribute(TARIFF_TYPE, typeTariff);
			request.setAttribute(ACTION, action);
			page = ADMIN_TARIFFS;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to display tariffs!");
			page = ERROR_PAGE;
		}
		return page;
	}
}
