package com.epam.training.provider.command.impl;


import static com.epam.training.provider.util.Permanent.*;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class SearchOneTariffCommand implements Command{
	private final static String TARIFF = "tariff";
	
	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		Tariff tariff = null;
		
		int id = Integer.parseInt(request.getParameter(TARIFF_ID));
		
		try {
			
			tariff = service.tariffById(id);
			request.setAttribute(TARIFF, tariff);
			page = TARIFF_PAGE;
			
		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to show a tariff card" + e.getMessage());
			page = ERROR_PAGE;
			
		}
		return page;
	}

}
