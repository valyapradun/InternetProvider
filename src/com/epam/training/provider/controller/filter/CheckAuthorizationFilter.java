package com.epam.training.provider.controller.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.controller.Controller;

import static com.epam.training.provider.util.Permanent.*;

public class CheckAuthorizationFilter implements Filter {
	private final static Logger logger = LogManager.getLogger(Controller.class.getName());
	private int role;
	private static Map<Integer, String> roleMap = new HashMap<Integer, String>();

	static {
		roleMap.put(0, "admin");
		roleMap.put(1, "user");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		String action = request.getParameter(ACTION);
		if (action == null) {
			action = ACTION_SHOW_INDEX_PAGE;
		}

		boolean checkSecurity = checkAction(action, securityAction);

		if (checkSecurity) {
			HttpSession session = request.getSession(false);
			User loggedUser = (User) session.getAttribute(USER);
			securityUser(loggedUser, req, resp, chain);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	public boolean checkAction(String action, String[][] securityAction) {
		for (int i = 0; i < securityAction.length; i++) {
			for (int j = 0; j < securityAction[i].length; j++) {
				if (securityAction[i][j].equals(action)) {
					role = i;
					return true;
				}
			}
		}
		return false;
	}

	private void securityUser(User loggedUser, ServletRequest req, ServletResponse resp, FilterChain chain)	throws IOException, ServletException {
		if (loggedUser != null) {
			String userRole = loggedUser.getRole();
			
			if (userRole.equals(roleMap.get(role))) {
				chain.doFilter(req, resp);
			} else {
				errorSecurity(req, resp);
			}

		} else {
			errorSecurity(req, resp);
		}

	}

	private void errorSecurity(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setAttribute(ERROR_MESSAGE, "For viewing of this page you have to be correctly authorized! ");
		
		logger.log(Level.ERROR, "There is the attempt to visit the page without authorization! ");
		
		RequestDispatcher disp = request.getRequestDispatcher(ERROR_PAGE);
		disp.forward(request, response);
	}

}
