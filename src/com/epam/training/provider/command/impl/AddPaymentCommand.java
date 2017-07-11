package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.PaymentType;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class AddPaymentCommand implements Command {
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		double ammount = Double.parseDouble(request.getParameter(PAYMENT_AMMOUNT));
		
		PaymentType type = PaymentType.REFILL;

		Calendar currentDate = Calendar.getInstance();
		Date date = currentDate.getTime();

		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		int userId = ((User) user).getId();

		Payment payment = new Payment(type, ammount, date, userId);

		try {
			
			service.addPayment(payment);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_SHOW_USER_PAGE;
			
		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, "Adding a transaction wasn't executed! " + e.getMessage());
			page = ERROR_PAGE;
			
		}
		return page;

	}

}
