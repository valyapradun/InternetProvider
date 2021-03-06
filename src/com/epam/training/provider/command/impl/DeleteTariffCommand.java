package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;
/**
 * Class for implementation of the command 'Delete the tariff' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class DeleteTariffCommand implements Command {
	private final static Logger logger = LogManager.getLogger(DeleteTariffCommand.class.getName());
	private final static String SUCCESS = "The tarif is successfully deleted, id - ";
	private final static String UNSUCCESS = "It is impossible to delete the tariff!";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	/**
	 * Method for processing of action of the administrator - 'Delete the tariff'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		
		int id = Integer.parseInt(request.getParameter(TARIFF_ID));
		
		try {
			
			service.deleteTariff(id);
			
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, SUCCESS + id);
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			logger.log(Level.INFO, "Tariff (id: " + id + ") has been deleted by the admin (session id:" + request.getSession(false).getId() + ")");
			
		} catch (ServiceException | ValidateException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			page = ERROR_PAGE;
			logger.log(Level.ERROR, e);
			
		}
		return page;
	}

}
