package com.epam.training.provider.controller.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.dao.connectionPool.ConnectionPool;
import com.epam.training.provider.dao.connectionPool.ConnectionPoolException;

/**
 * Class of the ServletContextListener. 
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ServletContextListenerImpl implements ServletContextListener {
	private final static Logger logger = LogManager.getLogger(ServletContextListenerImpl.class.getName());
	
	/**
	 * Method for initialization of a context of the application.
	 * To define the absolute path to the application.
	 * 
	 * @param event {@link ServletContextEvent}
	 *           
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		System.setProperty("rootPath", context.getRealPath("/"));
	}

	
	/**
	 * Method for removal of a context of a servlet.
	 * СonnectionPool is cleaned.
	 * 
	 * @param sce {@link ServletContextEvent}
	 *           
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ConnectionPool connectionPool = null;

		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Wasn't executed ConnectionPool.getInstance()!");
		}

		connectionPool.shutdown();
	}

}
