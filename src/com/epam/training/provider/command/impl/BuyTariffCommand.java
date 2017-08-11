package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'Buy the tariff' by user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class BuyTariffCommand implements Command {
	private final static Logger logger = LogManager.getLogger(BuyTariffCommand.class.getName());
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
		logger.log(Level.INFO, "Purchase of a tariff: ID user - " + userID + ", ID tariff - " + tariffID);

		try {

			service.buyTariff(userID, tariffID);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_SHOW_USER_PAGE;

		} catch (ServiceException e) {

			request.setAttribute(ERROR_MESSAGE, "It is impossible to buy of Tariff! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;

		} catch (ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, e.getMessage());
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}

		return page;
	}

}
