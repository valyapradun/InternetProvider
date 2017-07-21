package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Ban;
import com.epam.training.provider.dao.BanDAO;
import com.epam.training.provider.dao.connectionPool.ConnectionPool;
import com.epam.training.provider.dao.connectionPool.ConnectionPoolException;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class BanDAOImpl implements BanDAO{
	private final static Logger logger = LogManager.getLogger(BanDAOImpl.class.getName());
	private final static String SQL_NEW_BAN = "INSERT INTO provider.ban (user_id, administrator_id, start_date, reason) VALUES (?, ?, ?, ?)";
    
	private static ConnectionPool connectionPool;
	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Don't create Pool Connection!" + e);
			throw new DAORuntimeException("Don't create Pool Connection!", e);
		}
	}
	
	@Override
	public void addNew(Ban newBan) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			
			statement = connection.prepareStatement(SQL_NEW_BAN);
			statement.setInt(1, newBan.getUserId());
			statement.setInt(2, newBan.getAdminId());
			statement.setDate(3, new java.sql.Date(newBan.getStartDate().getTime()));
			statement.setString(4, newBan.getReason());

			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the ban. ", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

		
	}

}
