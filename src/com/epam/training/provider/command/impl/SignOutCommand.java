package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;

public class SignOutCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;
		HttpSession session = request.getSession(false);
		User loggedUser = (User) session.getAttribute(USER);

		if (loggedUser == null) {
			page = INDEX;
		} 
		else {
			page = INDEX;
			session.invalidate();
		}
		return page;
	}

}
