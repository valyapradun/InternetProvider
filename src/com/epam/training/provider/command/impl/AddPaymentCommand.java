package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.PaymentType;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
import com.epam.training.provider.service.impl.Validate;

/**
 * Class for implementation of the command 'Payment's refill'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class AddPaymentCommand implements Command {
	private final static Logger logger = LogManager.getLogger(AddPaymentCommand.class.getName());
	private final static String UNSUCCESS = "Adding a payment wasn't executed! ";
	
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	
	/**
	 * Method for processing of action of the user - 'Payment's refill'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		String checkAmount = Validate.checkDigitalSymbol(request.getParameter(PAYMENT_AMOUNT));	
		if (checkAmount.length() != 0) {
			
			request.setAttribute(INFO_MESSAGE, checkAmount);
			page = LIST_PAYMENTS_PAGE;
			
			logger.log(Level.ERROR, checkAmount);
		}
		else {
			Payment payment = createPaymentFromRequest(request);
			logger.log(Level.INFO, "Payment of the ID user (" + request.getSession(false) + "): " + payment);

			try {
				service.addPayment(payment);
				request.setAttribute(REDIRECT_PARAMETER, OK);
				page = request.getServletPath() + ACTION_SHOW_USER_PAGE;
				
			} catch (ServiceException | ValidateException e) {
				request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
				page = ERROR_PAGE;
				logger.log(Level.ERROR, e);
			}
		}
		return page;
		
	}
	
	
	/**
	 * Method for creatinging of payment from the request.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return payment {@link Payment}         
	 */
	private Payment createPaymentFromRequest(HttpServletRequest request) {
		double amount = Double.parseDouble(request.getParameter(PAYMENT_AMOUNT));
		
		PaymentType type = PaymentType.REFILL;

		Calendar currentDate = Calendar.getInstance();
		Date date = currentDate.getTime();

		HttpSession session = request.getSession(false);
		Object user = session.getAttribute(USER);
		int userId = ((User) user).getId();

		Payment payment = new Payment(type, amount, date, userId);
		
		return payment;
	}

}
