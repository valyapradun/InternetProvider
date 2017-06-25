package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;

public class SignOutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		HttpSession session = request.getSession(false);
		System.out.println("SignOut session: " + session);
		User loggedUser = (User) session.getAttribute("user");
		System.out.println("SignOut user: " + loggedUser);
		
		if (loggedUser == null) {
			page = "user_main.jsp";
			
			session = request.getSession(false);
			System.out.println("old session: " + session);
		} 
		else {
			page = "index.jsp";
			session.invalidate();
			
			session = request.getSession(false);
			System.out.println("new session: " + session);
		}
		return page;
	}

}
