package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class BuyTariffCommand implements Command {
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		HttpSession session = request.getSession(false);
		User user = (User) session.getAttribute(USER);
		int userID = user.getId();

		int tariffID = Integer.parseInt(request.getParameter(TARIFF_ID));

		try {

			service.buyTariff(userID, tariffID);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_SHOW_USER_PAGE;

		} catch (ServiceException e) {

			request.setAttribute(ERROR_MESSAGE, "It is impossible to buy of Tariff! " + e.getMessage());
			page = ERROR_PAGE;

		} catch (ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			page = ERROR_PAGE;
			
		}

		return page;
	}

}
