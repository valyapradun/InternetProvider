package com.epam.training.provider.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import com.epam.training.provider.command.CommandChooser;
import static com.epam.training.provider.util.Permanent.*;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
		System.out.println("servlet constructor");  		// for me
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("servlet init method");    		// for me
	}

	public void destroy() {
		System.out.println("servlet destroy method"); 		// for me
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet service method");   	// for me
		checkAuthorization(request);
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doGet method"); 	   // for me
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doPost method");    	// for me
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ACTION);
		System.out.println("action: " + action);           // for me
		if (action == null) {
			action = INDEX;
		}
		
		Command command = CommandChooser.chooseCommand(action);
		String page = command.execute(request, response);

		if (action.equals(SIGN_IN)) {
			response.sendRedirect(request.getContextPath() + page);
		} else {
			forward(request, response, page);
		}
	}

	public void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		RequestDispatcher disp = request.getRequestDispatcher(page);
		disp.forward(request, response);
	}

	public void checkAuthorization(HttpServletRequest request) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		
		System.out.println("for future check access");     // for me
	}
}
