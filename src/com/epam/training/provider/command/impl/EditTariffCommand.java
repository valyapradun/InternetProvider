package com.epam.training.provider.command.impl;

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
import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;
/**
 * Class for implementation of the command 'Edit the tariff' by administrator.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class EditTariffCommand implements Command {
	private final static Logger logger = LogManager.getLogger(EditTariffCommand.class.getName());
	private final static String SUCCESS = "The tarif is successfully edited, id - ";
	private final static String UNSUCCESS = "It is impossible to edit tariff's card! ";
	private final static String UNSUCCESS_VALIDATION = "The tarif hasn't been edited because: ";

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	
	/**
	 * Method for processing of action of the administrator - 'Edit the tariff'.
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
			service.editTariff(tariff);
			
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, SUCCESS + tariff.getId());
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			logger.log(Level.INFO, "Tariff (id: " + tariff.getId() + ") has been edited by the admin (session id:" + request.getSession(false).getId() + ")");
			
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
			page = ERROR_PAGE;
			logger.log(Level.ERROR, e);
			
		} catch (ValidateException e) {
			request.setAttribute(REDIRECT_PARAMETER, OK);
			request.getSession(false).setAttribute(INFO_MESSAGE, UNSUCCESS_VALIDATION + e.getMessage());
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			logger.log(Level.ERROR, e);
			
		}
		return page;
	}

	
	
	/**
	 * Method for creation of a tariff from request parameters.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return Tariff {@link Tariff}
	 *           
	 */
	private Tariff createTariffFromRequest(HttpServletRequest request){
		
		int id = Integer.parseInt(request.getParameter(TARIFF_ID));

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
		
		Tariff tariff = new Tariff(id, name, type, price, size, speed, picture);

		return tariff;
	}
	
	
	/**
	 * Method for normalization of empty value of parameter.
	 * 
	 * @param parameter {@link String}
	 * @return normalized parameter {@link String}
	 *           
	 */
	private String normalize(String parameter) {
		if (parameter.equals("")) {
			parameter = "0";
		}
		return parameter;
	}

}
