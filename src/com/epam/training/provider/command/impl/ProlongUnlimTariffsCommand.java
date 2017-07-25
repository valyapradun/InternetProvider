package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.factory.ServiceFactory;

public class ProlongUnlimTariffsCommand implements Command{
	private final static Logger logger = LogManager.getLogger(ProlongUnlimTariffsCommand.class.getName());
	
	private final PaymentService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getPaymentService();

	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		try {
			service.prolongUnlimTariffs();
			logger.log(Level.INFO, "Admin (session id:" + request.getSession(false).getId() + ") has prolonged unlim tariffs");
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_DISPLAY_USERS;
			
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "Prolonging unlim tariffs wasn't executed! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
		}
		
		return page;
	}
	
	
	
	/*SELECT user_to_tariff.user_id FROM provider.user_to_tariff 
	JOIN  provider.tariff 
	ON user_to_tariff.tariff_id = tariff.id
	WHERE tariff.tariff_type_id = 1
	AND user_to_tariff.end is NULL;*/
	
	
	/*SELECT user_to_tariff.user_id, user_to_tariff.tariff_id  FROM provider.user_to_tariff 
	JOIN  provider.tariff 
	ON user_to_tariff.tariff_id = tariff.id
	WHERE tariff.tariff_type_id = 1
	AND user_to_tariff.end is NULL;*/
	
	// с checkbalance false
	
	/*SELECT user_to_tariff.id, user_to_tariff.user_id, user_to_tariff.tariff_id, user_to_tariff.begin  
	FROM provider.user_to_tariff 
	JOIN  provider.tariff 
	ON user_to_tariff.tariff_id = tariff.id
	WHERE tariff.tariff_type_id = 1
	AND user_to_tariff.end is NULL
	AND user_to_tariff.begin  <= CURDATE() - INTERVAL 30 DAY;*/
	
	//закрываем старый, покупаем новый без проверки баланса и покупаем новый
	/*UPDATE provider.user_to_tariff
	SET end = CURDATE()
	WHERE id = 6;*/
	}

