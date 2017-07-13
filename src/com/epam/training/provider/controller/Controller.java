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

public class Controller extends HttpServlet {
	private final static Logger logger = LogManager.getLogger(Controller.class.getName());
	
	private static final long serialVersionUID = 1L;
	private final CommandProvider provider = new CommandProvider();

	public Controller() {
		super();
	}

	
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		
	//	String prefix = getServletContext().getRealPath("/");
	//	String filename = getInitParameter("init_log4j");
	//	if (filename != null) {
	//	PropertyConfigurator.configure(prefix + filename);
	//	}
	}
	
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkAuthorization(request);  // for future check access
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

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

	public void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher(page);
		disp.forward(request, response);
	}

	public void checkAuthorization(HttpServletRequest request) {  // for future check access
		// HttpSession session = request.getSession(true);
		// User user = (User) session.getAttribute("user");
		// System.out.println("for future check access"); // for me
	}
}
