package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;

public class ShowIndexPageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute(USER);
		request.setAttribute(USER, user);
		String page = INDEX_PAGE;
		return page;
	}

}
