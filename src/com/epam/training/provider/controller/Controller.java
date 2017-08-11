package com.epam.training.provider.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.command.CommandProvider;

import static com.epam.training.provider.util.Permanent.*;
/**
 * Class of the servlet. 
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class Controller extends HttpServlet {
	private final static Logger logger = LogManager.getLogger(Controller.class.getName());
	
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		super();
	}
	
	
	/**
	 * Method for distinction of methods of request (GET or POST).
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 *           
	 */
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}
	
	
	/**
	 * Method for processing of GET-request.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 *           
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	
	/**
	 * Method for processing of POST-request.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 *           
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	
	/**
	 * Method for processing of requests: reads the parameter at request 'action', calls method 'execute' for 
	 * specific action-command, receives jsp-page and forward or redirect transition to the page.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 *           
	 */
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ACTION);
		
		if (action == null) {
			action = ACTION_SHOW_INDEX_PAGE;
		}
		logger.log(Level.INFO, "action: " + action);
	
		Command command = provider.getCommand(action);
		String page = command.execute(request, response);
		
		logger.log(Level.INFO, "page: " + page);
		if (request.getAttribute(REDIRECT_PARAMETER) == null) {
			forward(request, response, page);
		} else {
			response.sendRedirect(request.getContextPath() + page);
		}
	}

	
	/**
	 * Method for a call method 'forward' RequestDispatcher.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @param page {@link String}
	 *           
	 */
	public void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher(page);
		disp.forward(request, response);
	}

}
