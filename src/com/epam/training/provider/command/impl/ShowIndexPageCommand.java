package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;
/**
 * Class for implementation of the command 'Show index-page'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ShowIndexPageCommand implements Command {
	private final static Logger logger = LogManager.getLogger(ShowIndexPageCommand.class.getName());

	
	/**
	 * Method for processing of action of the user - 'Show index-page'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		
		request.setAttribute(USER, user);
		logger.log(Level.INFO, "User (session id:" + request.getSession(false).getId() + ") opened index.jsp.");
		
		return INDEX_PAGE;
		
	}

}
