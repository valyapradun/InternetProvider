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
 * Class for implementation of the command 'Sign Out'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class SignOutCommand implements Command {
	private final static Logger logger = LogManager.getLogger(SignOutCommand.class.getName());

	
	/**
	 * Method for processing of action of the user - 'Sign Out'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = INDEX_PAGE;
		
		HttpSession session = request.getSession(false);
		User loggedUser = (User) session.getAttribute(USER);

		if (loggedUser != null) {
			session.invalidate();
			logger.log(Level.INFO, "Successful attempt to sign out - session: " + session.getId() + ", user role: " + loggedUser.getRole() + ", userID: " + loggedUser.getId());
		}
		
		return page;
	}

}
