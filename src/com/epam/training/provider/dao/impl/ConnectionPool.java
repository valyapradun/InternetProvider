package com.epam.training.provider.dao.impl;

import static com.epam.training.provider.dao.impl.DBParameter.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {
	private final static Logger logger = LogManager.getLogger(ConnectionPool.class.getName());

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

	public void shutdown() {
		try {
			closeQueue(usedPool);
			closeQueue(freePool);
		} catch (SQLException e) {
			logger.log(Level.ERROR, "Error closing the connection. ", e);
		}

	}

	private void closeQueue(BlockingQueue<Connection> queue) throws SQLException {
		Connection connection;

		while ((connection = queue.poll()) != null) {
			if (!connection.getAutoCommit()) {
				connection.commit();
			}
			connection.close();
		}
	}

}
