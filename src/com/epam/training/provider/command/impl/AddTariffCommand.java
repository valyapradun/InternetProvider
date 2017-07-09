package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class AddTariffCommand implements Command {

	private TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {

		String name = request.getParameter(TARIFF_NAME);
		byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes, StandardCharsets.UTF_8);

		double price = Double.parseDouble(request.getParameter(TARIFF_PRICE));
		double size = Double.parseDouble(normalize(request.getParameter(TARIFF_SIZE)));
		int speed = Integer.parseInt(normalize(request.getParameter(TARIFF_SPEED)));
		TariffType type = TariffType.valueOf(request.getParameter(TARIFF_TYPE).toUpperCase());

		String picture = request.getParameter(TARIFF_PICTURE);
		bytes = picture.getBytes(StandardCharsets.ISO_8859_1);
		picture = new String(bytes, StandardCharsets.UTF_8);

		Tariff tariff = new Tariff(name, type, price, size, speed, picture);
		String page = null;

		try {
			service.addTariff(tariff);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_TARIFFS;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "Adding a tariff wasn't executed!" + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;
	}

	public String normalize(String parameter) {
		if (parameter.equals("")) {
			parameter = "0";
		}
		return parameter;
	}

}
