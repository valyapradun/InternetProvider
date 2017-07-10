package com.epam.training.provider.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.epam.training.provider.command.Command;
import com.epam.training.provider.command.CommandChooser;

import static com.epam.training.provider.util.Permanent.*;

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String INDEX = "index";

	public Controller() {
		super();
		System.out.println("servlet constructor"); // for me
	}

	public void init(ServletConfig config) throws ServletException {
		System.out.println("servlet init method"); // for me
	}

	public void destroy() {
		System.out.println("servlet destroy method"); // for me
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet service method"); // for me
		checkAuthorization(request);  // for future check access
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doGet method"); // for me
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doPost method"); // for me
		processRequest(request, response);
	}

	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter(ACTION);
		System.out.println("action: " + action); // for me
		if (action == null) {
			action = INDEX;
		}

		Command command = CommandChooser.chooseCommand(action);
		String page = command.execute(request, response);
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
