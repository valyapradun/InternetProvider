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
/**
 * Class of the filter for checking authorisation and locking secure pages. 
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class CheckAuthorizationFilter implements Filter {
	private final static Logger logger = LogManager.getLogger(Controller.class.getName());
	private int role;
	private static Map<Integer, String> roleMap = new HashMap<Integer, String>();

	static {
		roleMap.put(0, "admin");
		roleMap.put(1, "user");
	}

	
	/**
	 * Method for checks of authorization and ban of access to secure pages.
	 * 
	 * @param req {@link ServletRequest}
	 * @param resp {@link ServletResponse}
	 * @param chain {@link FilterChain}
	 *           
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;

		String action = request.getParameter(ACTION);
		if (action == null) {
			action = ACTION_SHOW_INDEX_PAGE;
		}

		boolean checkSecurity = checkAction(action, SECURITY_ACTION);

		if (checkSecurity) {
			HttpSession session = request.getSession(false);
			User loggedUser = (User) session.getAttribute(USER);
			securityUser(loggedUser, req, resp, chain);
		} else {
			chain.doFilter(req, resp);
		}
	}
	
	
	/**
	 * Method for completion of work of the filter.
	 *            
	 */
	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	
	/**
	 * Method to start Life cycle of the filter.
	 *            
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	
	/**
	 * Method for action check. If action belongs in SECURITY_ACTION will return 'true' and role will become 0 or 1.
	 * If action belongs in first line of SECURITY_ACTION, role will become 0.
	 * If action belongs in second line of SECURITY_ACTION, role will become 1.
	 *
	 * 
	 * @param action {@link String}
	 * @param securityAction {@link String[][]}
	 * @return true or false - {@link boolean}  
	 *          
	 */
	private boolean checkAction(String action, String[][] securityAction) {
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

	
	/**
	 * Method for user check: compares a role of the authorized user and value of a variable 'role'.
	 * If they are not equal then calls method 'errorSecurity()'.
	 * 
	 * @param loggedUser {@link User}
	 * @param req {@link ServletRequest}
	 * @param resp {@link ServletResponse}
	 * @param chain {@link FilterChain}  
	 *          
	 */
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
	
	
	/**
	 * Method for showing error page.
	 * 
	 * @param req {@link ServletRequest}
	 * @param resp {@link ServletResponse}
	 *          
	 */
	private void errorSecurity(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setAttribute(ERROR_MESSAGE, "For viewing of this page you have to be correctly authorized! ");
		
		logger.log(Level.ERROR, "There is the attempt to visit the page without authorization! ");
		
		RequestDispatcher disp = request.getRequestDispatcher(ERROR_PAGE);
		disp.forward(request, response);
	}

}
