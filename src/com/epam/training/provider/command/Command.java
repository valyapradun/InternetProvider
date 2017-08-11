package com.epam.training.provider.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface for pattern Command .
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface Command {
	/**
	 * Method for processing of action of the user.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	public String execute(HttpServletRequest request, HttpServletResponse response);
}
