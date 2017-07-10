package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.bean.TransactionType;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TransactionService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class RefillCommand implements Command {
	private TransactionService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTransactionService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		double ammount = Double.parseDouble(request.getParameter(TRANSACTION_AMMOUNT));
		TransactionType type = TransactionType.REFILL;

		Calendar currentDate = Calendar.getInstance();
		Date date = currentDate.getTime();

		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		int userId = ((User) user).getId();

		Transaction transaction = new Transaction(type, ammount, date, userId);

		try {
			service.addTransaction(transaction);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_USER_MAIN;
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "Adding a transaction wasn't executed! " + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;

	}

}
