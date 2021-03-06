package com.epam.training.provider.command.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;

/**
 * Class for implementation of the command 'Add the tariff' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class AddTariffCommand implements Command {
	private final static Logger logger = LogManager.getLogger(AddTariffCommand.class.getName());
	private final static String UNSUCCESS = "Adding a tariff wasn't executed! ";
	private final static String SUCCESS = "The tarif is successfully added.";
	private final static String UNSUCCESS_INFO = "The tarif hasn't been added because: ";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	
	/**
	 * Method for processing of action of the user - 'Add the tariff'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		Tariff tariff = createTariffFromRequest(request);
		
		try {

			service.addTariff(tariff);
			
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, SUCCESS);
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			logger.log(Level.INFO, "Tariff (" + tariff + ") has been addeted by the admin (session id:"	+ request.getSession(false).getId() + ")");

		} catch (ServiceException e) {
			
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			page = ERROR_PAGE;
			logger.log(Level.ERROR, e);

		} catch (ValidateException e) {

			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, UNSUCCESS_INFO + e.getMessage());
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			logger.log(Level.ERROR, e);
		}
		
		
		return page;
	}

	
	/**
	 * Method for creatinging of tariff from the request.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return tariff {@link Tariff}         
	 */
	private Tariff createTariffFromRequest(HttpServletRequest request) {

		String name = request.getParameter(TARIFF_NAME);
		byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes, StandardCharsets.UTF_8);

		double price = Double.parseDouble(normalize(request.getParameter(TARIFF_PRICE)));
		double size = Double.parseDouble(normalize(request.getParameter(TARIFF_SIZE)));
		int speed = Integer.parseInt(normalize(request.getParameter(TARIFF_SPEED)));
		TariffType type = TariffType.valueOf(request.getParameter(TARIFF_TYPE).toUpperCase());

		String picture = request.getParameter(TARIFF_PICTURE);
		bytes = picture.getBytes(StandardCharsets.ISO_8859_1);
		picture = new String(bytes, StandardCharsets.UTF_8);

		Tariff tariff = new Tariff(name, type, price, size, speed, picture);

		return tariff;
	}

	
	/**
	 * Method for normalization of the empty parameter.
	 * 
	 * @param parameter {@link String}
	 * @return parameter {@link String}         
	 */
	private String normalize(String parameter) {
		if (parameter.equals("")) {
			parameter = "0";
		}
		return parameter;
	}

}
