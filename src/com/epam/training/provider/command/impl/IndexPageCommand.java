package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;

public class IndexPageCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		System.out.println("user index.jsp:" + user);
		request.setAttribute("user", user);
		String page = "/index.jsp";
		return page;
	}

}
