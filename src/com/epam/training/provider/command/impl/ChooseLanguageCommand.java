package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;

public class ChooseLanguageCommand implements Command {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		String language = request.getParameter(LANGUAGE);

		HttpSession session = request.getSession(false);
		session.setAttribute(LANGUAGE, language);
		request.setAttribute(REDIRECT_PARAMETER, "Yes");

		String backPage = request.getHeader(REFERER);

		// cut out from a way of the page "request.getContextPath()"
		int cutIndex = backPage.lastIndexOf(request.getContextPath());
		page = backPage.substring(cutIndex + request.getContextPath().length());

		return page;
	}

}
