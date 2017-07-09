package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.bean.TransactionType;
import com.epam.training.provider.dao.TransactionDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class TransactionDAOImpl implements TransactionDAO {
	private static ConnectionPool connectionPool;

	private final static String SQL_ALL_TRANSACTIONS = "SELECT transaction.id, transaction.type, transaction.ammount, transaction.date FROM provider.transaction";
	private final static String SQL_NEW_TRANSACTION = "INSERT INTO provider.transaction (type, ammount, date, user_id) VALUES (?, ?, ?, ?)";
	private final static String SQL_UPDATE_BALANCE = "UPDATE provider.user SET user.balance = user.balance + ? WHERE user.id = ?";
	
	//UPDATE provider.user SET user.balance = user.balance + 20 WHERE user.id = 6
	
	//"INSERT INTO provider.transaction (type, ammount, date, user_id) VALUES ('refill', '25', '2017-07-01', '6')"
	
	
	
	
	
	private final static String TRANSACTION_ID = "id";
	private final static String TRANSACTION_TYPE = "type";
	private final static String TRANSACTION_AMMOUNT = "ammount";
	private final static String TRANSACTION_DATE = "date";
	private final static String TRANSACTION_USER_ID = "userId";

	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DAORuntimeException("Don't create Pool Connection!", e);
		}
	}

	@Override
	public List<Transaction> searchWithParameters(HashMap<String, String> parameters) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Transaction> transactions = new ArrayList<Transaction>();

		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String where = "";

			if (!parameters.isEmpty() && (parameters.get(TRANSACTION_USER_ID) != null)) {
				where = " WHERE transaction.user_id = " + parameters.get(TRANSACTION_USER_ID);
			}

			resultSet = statement.executeQuery(SQL_ALL_TRANSACTIONS + where);
			while (resultSet.next()) {
				int id = resultSet.getInt(TRANSACTION_ID);
				TransactionType type = TransactionType.valueOf(resultSet.getString(TRANSACTION_TYPE).toUpperCase());
				double ammount = resultSet.getDouble(TRANSACTION_AMMOUNT);
				Date date = resultSet.getDate(TRANSACTION_DATE);
				Transaction transaction = new Transaction(id, type, ammount, date,
						Integer.parseInt(parameters.get(TRANSACTION_USER_ID)));
				transactions.add(transaction);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot read transactions. ", e);
		} finally {
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

			connectionPool.freeConnection(connection);
		}

		return transactions;
	}



	@Override
	public void addNew(Transaction transaction) throws DAOException {
		if (transaction == null) {
			throw new DAOException("The transaction for adding is equal to null!");
		}

		Connection connection = null;
		PreparedStatement statementAdd = null;
		PreparedStatement statementUpdate = null;

		try {
			connection = connectionPool.takeConnection();
			connection.setAutoCommit(false);
			
			
			
			statementAdd = connection.prepareStatement(SQL_NEW_TRANSACTION);
			statementAdd.setString(1, transaction.getType().toString().toLowerCase());
			System.out.println("type transaction: " + transaction.getType().toString().toLowerCase());
			
			statementAdd.setDouble(2, transaction.getAmmount());
			System.out.println("ammount transaction: " + transaction.getAmmount());
			
			statementAdd.setDate(3, new java.sql.Date(transaction.getDate().getTime()));
			System.out.println("date transaction: " + new java.sql.Date(transaction.getDate().getTime()));
			
			statementAdd.setInt(4, transaction.getUserId());
			System.out.println("user id in transaction: " + transaction.getUserId());
	
			
		//	ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
	
			statementAdd.executeUpdate();
			

			statementUpdate = connection.prepareStatement(SQL_UPDATE_BALANCE);
			statementUpdate.setDouble(1, transaction.getAmmount());
			statementUpdate.setInt(2, transaction.getUserId());
			statementUpdate.executeUpdate();

			connection.commit();
			
			
			
			connection.setAutoCommit(true);
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the transaction. ", e);
		} finally {
			try {
				statementAdd.close();
			} catch (SQLException e) {
				// logger.log(Level.ERROR, "Statement isn't closed.");
			}
			try {
				statementUpdate.close();
			} catch (SQLException e) {
				// logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

	}

}
