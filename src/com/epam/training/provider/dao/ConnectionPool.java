package com.epam.training.provider.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
	private BlockingQueue<Connection> freePool;
	private BlockingQueue<Connection> usedPool;
	private int poolSize = 5;

	public ConnectionPool() {
		this.freePool = new ArrayBlockingQueue<Connection>(poolSize);
		this.usedPool = new ArrayBlockingQueue<Connection>(poolSize);
	}

	public void createPool() throws ConnectionPoolException {
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

	public Connection takeConnection() throws ConnectionPoolException {
		Connection connection = null;
		if (freePool.size() == 0) {
			throw new ConnectionPoolException("The size of the connection pool is 0!");
		}
		try {	
			connection = freePool.take();
			usedPool.add(connection);
		} catch (InterruptedException e) {
			throw new ConnectionPoolException("Error connecting to the data source. ", e);
		}
		return connection;
	}
	
	/* public void closeConnection(Connection connection) {
	        if (connection != null) {
	            try {
	                pool.put(connection);
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	    }*/
}
