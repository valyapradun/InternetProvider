package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'Search all payments of user' by user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class SearchUserPaymentCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SearchUserPaymentCommand.class.getName());
	private static final String USER_ID = "userId";
	private static final String LIST_PAYMENTS = "transactions";
	
	private final PaymentService service;
	
	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		List<Payment> payments = null;

		String action = request.getParameter(ACTION);
		
		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		String userID = Integer.toString(((User) user).getId());
		
		Map<String, String> criteria = new HashMap<String, String>();
		criteria.put(USER_ID, userID);

		try {
			
			payments = service.listPaymentsWithCriteria(criteria);
			logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") opened list of payments.");
			request.setAttribute(ACTION, action);
			request.setAttribute(LIST_PAYMENTS, payments);
			page = LIST_PAYMENTS_PAGE;
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, "It is impossible to display transactions!");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		}

		return page;
	}

}
