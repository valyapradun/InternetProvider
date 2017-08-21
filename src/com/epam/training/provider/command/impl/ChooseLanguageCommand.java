package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;
/**
 * Class for implementation of the command 'Choose a language'.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ChooseLanguageCommand implements Command {

	/**
	 * Method for processing of action of the user - 'Choose a language'.
	 * 
	 * @param request {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @return jsp-page {@link String}          
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = null;

		String language = request.getParameter(LANGUAGE);

		HttpSession session = request.getSession(false);
		session.setAttribute(LANGUAGE, language);
		request.setAttribute(REDIRECT_PARAMETER, OK);

		String backPage = request.getHeader(REFERER);

		// cut out from a way of the page "request.getContextPath()"
		int cutIndex = backPage.lastIndexOf(request.getContextPath());
		page = backPage.substring(cutIndex + request.getContextPath().length());

		return page;
	}

}
