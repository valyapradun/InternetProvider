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

	private final TariffService service;

	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getTariffService();
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		Tariff tariff = getTariffFromRequest(request);
		
		try {
			service.editTariff(tariff);
			logger.log(Level.INFO, "Tariff (id: " + tariff.getId() + ") has been edited by the admin (session id:" + request.getSession(false).getId() + ")");
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			request.getSession(false).setAttribute(INFO_MESSAGE, "The tarif " + tariff.getId() + " is successfully edited.");
			
		} catch (ServiceException e) {
			request.setAttribute(ERROR_MESSAGE, "It is impossible to edit tariff's card! ");
			logger.log(Level.ERROR, e);
			page = ERROR_PAGE;
			
		} catch (ValidateException e) {
			logger.log(Level.ERROR, e);
			request.setAttribute(REDIRECT_PARAMETER, "Yes");
			page = request.getServletPath() + ACTION_DISPLAY_TARIFFS;
			request.getSession(false).setAttribute(INFO_MESSAGE, "The tarif " + tariff.getId() + " hasn't been edited because: " + e.getMessage());
			
		}
		return page;
	}

	
	private Tariff getTariffFromRequest(HttpServletRequest request){
		
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
	
	
	private String normalize(String parameter) {
		if (parameter.equals("")) {
			parameter = "0";
		}
		return parameter;
	}

}
