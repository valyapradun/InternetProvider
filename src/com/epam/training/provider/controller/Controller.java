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

public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public Controller() {
        super();
        System.out.println("servlet constructor");
    }

	public void init(ServletConfig config) throws ServletException {
		System.out.println("servlet init method");
	}

	public void destroy() {
		System.out.println("servlet destroy method");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet service method");
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doGet method");
		processRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("servlet doPost method");
//		response.setContentType("text/html");
//		String login = request.getParameter("login");
//		String password = request.getParameter("password");
//		PrintWriter out = response.getWriter();
//		out.println("Your login: " + login);
//		out.println("<br />Your password: " + password);
		processRequest(request, response);
	}
	
	public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String action = request.getParameter("action");
		Command command = CommandChooser.chooseCommand(action);
		String page = command.execute(request, response);
		forward(request, response, page);
	}
	
	public void forward(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException{
		RequestDispatcher disp = request.getRequestDispatcher(page);
		disp.forward(request, response);
	}
}
