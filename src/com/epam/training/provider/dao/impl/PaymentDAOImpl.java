package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.PaymentType;
import com.epam.training.provider.dao.PaymentDAO;
import com.epam.training.provider.dao.connectionPool.ConnectionPool;
import com.epam.training.provider.dao.connectionPool.ConnectionPoolException;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class PaymentDAOImpl implements PaymentDAO {
	private final static Logger logger = LogManager.getLogger(PaymentDAOImpl.class.getName());
	
	private final static String SQL_ALL_PAYMENTS = "SELECT transaction.id, transaction.type, transaction.ammount, transaction.date FROM provider.transaction";
	private final static String SQL_NEW_PAYMENT = "INSERT INTO provider.transaction (type, ammount, date, user_id) VALUES (?, ?, ?, ?)";
	private final static String SQL_UPDATE_BALANCE = "UPDATE provider.user SET user.balance = user.balance + ? WHERE user.id = ?";
	private final static String SQL_ADD_TARIFF = "INSERT INTO provider.user_to_tariff (user_id, tariff_id, begin) VALUES (?, ?, ?)";
	
	private final static String PAYMENT_ID = "id";
	private final static String PAYMENT_TYPE = "type";
	private final static String PAYMENT_AMOUNT = "ammount";
	private final static String PAYMENT_DATE = "date";
	private final static String PAYMENT_USER_ID = "userId";

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
	public List<Payment> searchWithParameters(Map<String, String> parameters) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Payment> payments = new ArrayList<Payment>();
		String where = "";

		try {
			
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ALL_PAYMENTS);
		
			if (!parameters.isEmpty() && (parameters.get(PAYMENT_USER_ID) != null)) {
				where = " WHERE transaction.user_id = " + parameters.get(PAYMENT_USER_ID);
			}

			resultSet = statement.executeQuery(SQL_ALL_PAYMENTS + where);
			
			while (resultSet.next()) {
				
				int id = resultSet.getInt(PAYMENT_ID);
				PaymentType type = PaymentType.valueOf(resultSet.getString(PAYMENT_TYPE).toUpperCase());
				double amount = resultSet.getDouble(PAYMENT_AMOUNT);
				Date date = resultSet.getDate(PAYMENT_DATE);
				
				Payment payment = new Payment(id, type, amount, date, Integer.parseInt(parameters.get(PAYMENT_USER_ID)));
				payments.add(payment);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot read transactions. ", e);
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

		return payments;
	}



	@Override
	public void addNew(Payment payment) throws DAOException {
		Connection connection = null;
		PreparedStatement statementAdd = null;
		PreparedStatement statementUpdate = null;

		try {
			connection = connectionPool.takeConnection();
			
			connection.setAutoCommit(false);
			
			statementAdd = connection.prepareStatement(SQL_NEW_PAYMENT);
			statementAdd.setString(1, payment.getType().toString().toLowerCase());
			statementAdd.setDouble(2, payment.getAmount());
			statementAdd.setDate(3, new java.sql.Date(payment.getDate().getTime()));
			statementAdd.setInt(4, payment.getUserId());
			statementAdd.executeUpdate();
			

			statementUpdate = connection.prepareStatement(SQL_UPDATE_BALANCE);
			statementUpdate.setDouble(1, payment.getAmount());
			statementUpdate.setInt(2, payment.getUserId());
			statementUpdate.executeUpdate();

			connection.commit();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the transaction. ", e);
		} finally {
			try {
				statementAdd.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}
			try {
				statementUpdate.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}
			
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Connection isn't setAutoCommit(true).");
			}
			connectionPool.freeConnection(connection);
		}

	}



	@Override
	public void buyNewTariff(Payment payment, int idTariff) throws DAOException {
		Connection connection = null;
		PreparedStatement statementInsert = null;
		PreparedStatement statementAdd = null;
		PreparedStatement statementUpdate = null;

		try {
			connection = connectionPool.takeConnection();
			
			connection.setAutoCommit(false);
			
			statementInsert = connection.prepareStatement(SQL_ADD_TARIFF);
			statementInsert.setInt(1, payment.getUserId());
			statementInsert.setInt(2, idTariff);
			statementInsert.setDate(3, new java.sql.Date(payment.getDate().getTime()));
			statementInsert.executeUpdate();
			
	
			statementAdd = connection.prepareStatement(SQL_NEW_PAYMENT);
			statementAdd.setString(1, payment.getType().toString().toLowerCase());
			statementAdd.setDouble(2, payment.getAmount());
			statementAdd.setDate(3, new java.sql.Date(payment.getDate().getTime()));
			statementAdd.setInt(4, payment.getUserId());
			statementAdd.executeUpdate();
			

			statementUpdate = connection.prepareStatement(SQL_UPDATE_BALANCE);
			statementUpdate.setDouble(1, - payment.getAmount());
			statementUpdate.setInt(2, payment.getUserId());
			statementUpdate.executeUpdate();

			connection.commit();
			
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the transaction. ", e);
		} finally {
			try {
				statementAdd.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}
			try {
				statementUpdate.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}
			
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "connection.setAutoCommit() isn't true.");
			}
			connectionPool.freeConnection(connection);
		}

		
	}

}
