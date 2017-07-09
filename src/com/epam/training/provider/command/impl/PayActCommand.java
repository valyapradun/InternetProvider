package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TransactionService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class PayActCommand implements Command {
	private TransactionService service;

	private static final String RESULT = "transactions";

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTransactionService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Transaction> transactions = null;
		String action = request.getParameter(ACTION);
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute("user");
		System.out.println("User: " + user);
		
		String userID = Integer.toString(((User)user).getId());
	System.out.println("userID: " + userID);
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("userId", userID);

		try {
			transactions = service.listTransactionWithParameters(parameters);
			request.setAttribute(ACTION, action);
			request.setAttribute(RESULT, transactions);
			page = USER_TRANSACTION;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "It is impossible to display transactions!" + e.getMessage());
			page = ERROR_PAGE;
		}

		return page;
	}

}
