package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class SearchUserPaymentCommand implements Command {
	private final PaymentService service;

	private static final String KEY = "userId";
	private static final String RESULT = "transactions";

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Payment> transactions = null;

		String action = request.getParameter(ACTION);
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		String userID = Integer.toString(((User) user).getId());
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put(KEY, userID);

		try {
			transactions = service.listTransactionWithParameters(parameters);
			request.setAttribute(ACTION, action);
			request.setAttribute(RESULT, transactions);
			page = USER_TRANSACTION;
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "It is impossible to display transactions!" + e.getMessage());
			page = ERROR_PAGE;
		}

		return page;
	}

}
