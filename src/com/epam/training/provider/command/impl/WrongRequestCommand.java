package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.training.provider.util.Permanent.*;

import com.epam.training.provider.command.Command;
/**
 * Class for implementation of the wrong command.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class WrongRequestCommand implements Command {
	private final static Logger logger = LogManager.getLogger(WrongRequestCommand.class.getName());

	/**
	 * Method for wrong command of the user.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") sent wrong request.");
		
		return ERROR_PAGE;
	}

}
