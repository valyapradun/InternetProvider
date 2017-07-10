package com.epam.training.provider.dao.impl;

import static com.epam.training.provider.dao.impl.DBParameter.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
	private static volatile ConnectionPool instance = null;
	private static BlockingQueue<Connection> freePool;
	private static BlockingQueue<Connection> usedPool;
	private static int poolSize = 5;

	private ConnectionPool() throws ConnectionPoolException {
		ResourceBundle resource = ResourceBundle.getBundle(RESOURCE_BUNDLE);
		String driver = resource.getString(DB_DRIVER);
		String url = resource.getString(DB_URL);
		String user = resource.getString(DB_USER);
		String pass = resource.getString(DB_PASSWORD);
		
		freePool = new ArrayBlockingQueue<Connection>(poolSize);
		usedPool = new ArrayBlockingQueue<Connection>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			try {
				Class.forName(driver);
				freePool.add(DriverManager.getConnection(url, user, pass));
			} catch (ClassNotFoundException e) {
				throw new ConnectionPoolException("Can't find database driver class. ", e);
			} catch (SQLException e) {
				throw new ConnectionPoolException("SQLException in ConnectionPool. ", e);
			}
		}
	}

	public static ConnectionPool getInstance() throws ConnectionPoolException {
		if (instance == null) {
			synchronized (ConnectionPool.class) {
				if (instance == null) {
					instance = new ConnectionPool();
				}
			}
		}
		return instance;
	}

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		try {
			connection = freePool.take();
			usedPool.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source! ", e);
		}
		return connection;
	}

	public void freeConnection(Connection connection) {
		if (connection != null) {
			freePool.add(connection);
			usedPool.remove(connection);
		}
	}

	public void closeConnection(Connection connection, Statement statement, ResultSet resultSet) {
		try {
			connection.close();
		} catch (SQLException e) {
			// logger.log(Level.ERROR, "Connection isn't return to the pool.");
		}

		try {
			resultSet.close();
		} catch (SQLException e) {
			// logger.log(Level.ERROR, "ResultSet isn't closed.");
		}

		try {
			statement.close();
		} catch (SQLException e) {
			// logger.log(Level.ERROR, "Statement isn't closed.");
		}
	}
	
	
	public void closeConnection(Connection connection, Statement statement) {
		try {
			connection.close();
		} catch (SQLException e) {
			// logger.log(Level.ERROR, "Connection isn't return to the pool.");
		}

		try {
			statement.close();
		} catch (SQLException e) {
			// logger.log(Level.ERROR, "Statement isn't closed.");
		}
	}
	
	
	
}
