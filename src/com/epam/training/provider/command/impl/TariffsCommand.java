package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.CATALOG;
import static com.epam.training.provider.util.Permanent.ERROR;

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
	private static final String TYPE = "type";
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
		String typeTariff = request.getParameter(TYPE);
		String action = request.getParameter(ACTION);
		HashMap<String,String> parameters = new HashMap<String, String>();
		parameters.put(KEY, typeTariff);
		try {
			tariffs = service.listTariffsWithParameters(parameters);
			request.setAttribute(RESULT, tariffs);
			request.setAttribute(TYPE, typeTariff);
			request.setAttribute(ACTION, action);
			page = "/admin_index.jsp";
		} catch (ServiceException e) {
			page = ERROR;
		}
		return page;
	}
}
