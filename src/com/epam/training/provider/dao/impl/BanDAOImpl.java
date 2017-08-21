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

/**
 * Class-implementation of DAO for the operations with a ban.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class BanDAOImpl implements BanDAO{
	private final static Logger logger = LogManager.getLogger(BanDAOImpl.class.getName());
	
	/** Default SQL request for adding new ban */ 
	private final static String SQL_NEW_BAN = "INSERT INTO provider.ban (user_id, administrator_id, start_date, reason) VALUES (?, ?, ?, ?)";
	
	/** Default SQL request for editing date of end of ban  */ 
	private final static String SQL_EDIT_BAN = "UPDATE provider.ban SET ban.end_date = ? WHERE ban.id = ?";
	
	/** Default SQL request for searching active ban of user  */
	private final static String SQL_ACTIVE_BAN = "SELECT ban.id, ban.user_id, ban.administrator_id, ban.reason FROM provider.ban WHERE ban.user_id = ? AND ban.end_date IS NULL";
	
	
	
	/** Default title of column from table 'ban'  */
	private final static String BAN_ID = "id";
	
	/** Default title of column from table 'ban'  */
	private final static String BAN_USER_ID = "user_id";
	
	/** Default title of column from table 'ban'  */
	private final static String BAN_ADMIN_ID = "administrator_id";
	
	/** Default title of column from table 'ban'  */
	private final static String BAN_REASON = "reason";

	
	
	/** Connection Pool from where take and return connection */
	private final static ConnectionPool connectionPool;
	
	/** Initialization connectionPool - receiving instance of class 'ConnectionPool' */
	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Don't create Pool Connection!" + e);
			throw new DAORuntimeException("Don't create Pool Connection!", e);
		}
	}
	
	
	
	/**
	 * Method for adding the ban to user (insert new row in the table 'ban').
	 * 
	 * @param newBan - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
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

	
	
	/**
	 * Method for editing date of end of ban (change on current date).
	 * 
	 * @param ban - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
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

	
	
	/**
	 * Method for searching active ban of user.
	 * 
	 * @param userID - {@link User#id}
	 * @return Ban - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
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
