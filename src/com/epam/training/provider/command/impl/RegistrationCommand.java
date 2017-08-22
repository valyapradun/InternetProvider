package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import com.epam.training.provider.service.factory.ServiceFactory;

import static com.epam.training.provider.util.Permanent.*;

import java.nio.charset.StandardCharsets;
/**
 * Class for implementation of the command 'Registration of new user'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class RegistrationCommand implements Command {
	private final static Logger logger = LogManager.getLogger(RegistrationCommand.class.getName());
	private final static String POST = "POST";
	private final static String UNSUCCESS = "It is impossible to registrate! ";
	private final static String UNSUCCESS_VALIDATION = "Wrong data:! ";
	
	private final UserService service;
	
	{
		ServiceFactory serviceObjectFactory = ServiceFactory.getInstance();
		service = serviceObjectFactory.getUserService();
	}

	
	/**
	 * Method for processing of action of the user - 'Registration of new user'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}
	 *           
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		if (request.getMethod().equals(POST)) {
			
			User user = createUserFromRequest(request);

			try {
				
				service.registration(user);
				logger.log(Level.INFO, "User (id: " + user.getId() + ") has been registered.");
				
				user = service.authorize(user.getLogin(), user.getPassword());
				logger.log(Level.INFO, "Attempt to sign in (login: " + user.getLogin() + ")");
				
				HttpSession session = request.getSession(true);
				session.setAttribute(USER, user);
				logger.log(Level.INFO, "Successful attempt to sign in - session: " + session.getId() + ", user role: " + user.getRole() + ", user ID: " + user.getId());
						
				request.setAttribute(REDIRECT_PARAMETER, OK);
				page = request.getServletPath() + ACTION_SHOW_USER_PAGE;

			} catch (ServiceException e) {
				
				request.setAttribute(ERROR_MESSAGE, UNSUCCESS);
				logger.log(Level.ERROR, e);
				page = ERROR_PAGE;
				
			} catch (ValidateException e) {
				
				request.setAttribute(USER, user);
				request.setAttribute(ERROR_MESSAGE, (UNSUCCESS_VALIDATION + e.getMessage()).split("!"));
				logger.log(Level.WARN, e);
				page = REGISTRATION_PAGE;
				
			}

		} else {
			
			page = REGISTRATION_PAGE;
			
		}

		return page;
	}
	
	
	/**
	 * Method for creation of a user from request parameters.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @return User {@link User}
	 *           
	 */
	private User createUserFromRequest(HttpServletRequest request){
		String login = request.getParameter(USER_LOGIN);
		String password = request.getParameter(USER_PASSWORD);

		String name = request.getParameter(USER_NAME);
		byte[] bytes = name.getBytes(StandardCharsets.ISO_8859_1);
		name = new String(bytes, StandardCharsets.UTF_8);

		String email = request.getParameter(USER_EMAIL);

		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setName(name);
		user.setEmail(email);
		
		return user;
	}

}
