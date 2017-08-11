package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	private final static String SQL_EDIT_BAN = "UPDATE provider.ban SET ban.end_date = ? WHERE ban.id = ?";
	private final static String SQL_ACTIVE_BAN = "SELECT ban.id, ban.user_id, ban.administrator_id, ban.reason FROM provider.ban WHERE ban.user_id = ? AND ban.end_date IS NULL";
	
	private final static String BAN_ID = "id";
	private final static String BAN_USER_ID = "user_id";
	private final static String BAN_ADMIN_ID = "administrator_id";
//	private final static String BAN_START_DATE = "start_date";
	private final static String BAN_REASON = "reason";

	
	private final static ConnectionPool connectionPool;
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

	@Override
	public void edit(Ban ban) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_EDIT_BAN);
			statement.setDate(1, new java.sql.Date(ban.getEndDate().getTime()));
			statement.setInt(2, ban.getId());
			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot edit the ban. ", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

		
	}

	@Override
	public Ban activeBan(int userID) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Ban ban = null;

		try {
			connection = connectionPool.takeConnection();
			
			statement = connection.prepareStatement(SQL_ACTIVE_BAN);
			statement.setInt(1, userID);
		
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(BAN_ID);
				int userId = resultSet.getInt(BAN_USER_ID);
				int adminId = resultSet.getInt(BAN_ADMIN_ID);
				String reason = resultSet.getString(BAN_REASON);
				
				ban = new Ban(id, userId, adminId, reason);
			}
			
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the ban. ", e);
		} finally {
			try {
				resultSet.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}
		return ban;
	}

}
