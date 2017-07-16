package com.epam.training.provider.controller.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.dao.impl.ConnectionPool;
import com.epam.training.provider.dao.impl.ConnectionPoolException;

public class ServletContextListenerImpl implements ServletContextListener {
	private final static Logger logger = LogManager.getLogger(ServletContextListenerImpl.class.getName());

	@Override
	public void contextInitialized(ServletContextEvent event) {
		ServletContext context = event.getServletContext();
		System.setProperty("rootPath", context.getRealPath("/"));
	}

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
