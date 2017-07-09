package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.ACTION_TARIFFS;
import static com.epam.training.provider.util.Permanent.ERROR;
import static com.epam.training.provider.util.Permanent.ERROR_PAGE;
import static com.epam.training.provider.util.Permanent.REDIRECT_PARAMETER;
import static com.epam.training.provider.util.Permanent.TARIFF_PRICE;

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
		
		double ammount = Double.parseDouble(request.getParameter("ammount"));
		TransactionType type = TransactionType.REFILL;
 
		Calendar currentDate = Calendar.getInstance();
		Date date = currentDate.getTime();
		
		
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute("user");

		int userId = ((User)user).getId();
		
		
		Transaction transaction = new Transaction(type, ammount, date, userId);
		System.out.println("transaction: " + transaction);
		try {
			service.addTransaction(transaction);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + "?action=user_main";
		} catch (ServiceException e) {
			request.setAttribute(ERROR, "Adding a transaction wasn't executed! " + e.getMessage());
			page = ERROR_PAGE;
		}
		return page;

	}

}
