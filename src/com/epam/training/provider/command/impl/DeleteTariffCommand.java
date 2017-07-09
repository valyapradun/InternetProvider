package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class DeleteTariffCommand implements Command {

	private TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter(TARIFF_ID));
		String page = null;

		try {
			service.deleteTariff(id);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_TARIFFS;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to delete the tariff!" + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;
	}

}
