package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
	private static volatile ConnectionPool instance = null;
	private static BlockingQueue<Connection> freePool;
	private static BlockingQueue<Connection> usedPool;
	private static int poolSize = 5;

	private ConnectionPool() throws ConnectionPoolException {
		freePool = new ArrayBlockingQueue<Connection>(poolSize);
		usedPool = new ArrayBlockingQueue<Connection>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			try {
				freePool.add(ConnectorDB.getConnection());
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
			System.out.println("Size freePool before: " + freePool.size());
			connection = freePool.take();
			System.out.println("Size freePool after: " + freePool.size());
			usedPool.add(connection);
			System.out.println("Size usedPool before: " + usedPool.size());
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
}
