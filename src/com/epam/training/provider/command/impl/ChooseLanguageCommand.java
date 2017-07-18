package com.epam.training.provider.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.epam.training.provider.command.Command;
import static com.epam.training.provider.util.Permanent.*;

import java.util.Locale;

public class ChooseLanguageCommand implements Command{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String page = "";
		String language = request.getParameter("lang");
		HttpSession session = request.getSession(false);
		session.setAttribute("lang", language);
		request.setAttribute(REDIRECT_PARAMETER, "Yes");
		request.setAttribute("lang", language);
		
		System.out.println("request.getRequestURI(): " + request.getRequestURI());
		System.out.println("request.getRequestURL(): " + request.getRequestURL());
		System.out.println("request.getHeader('referer'): " + request.getHeader("referer"));
		System.out.println("request.getContextPath(): " + request.getContextPath());
		
		String s1 = request.getHeader("referer");
		
		int index3 = s1.lastIndexOf(request.getContextPath()); 
		System.out.println("index3: " + index3);
		
		String substr1 = s1.substring(index3 + request.getContextPath().length()); // world
		System.out.println("substr1: " + substr1);
		
		page = substr1;
		
	
		
		
		
		
		// TODO Auto-generated method stub
		return page;
	}

}
