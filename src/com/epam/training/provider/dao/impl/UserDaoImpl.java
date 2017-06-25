package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;

public class UserDAOImpl implements UserDAO {
	private static ConnectionPool connectionPool;
	private static String salt;
	
	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("Don't create Pool Connection!", e);
		}
		ResourceBundle resource = ResourceBundle.getBundle("config");
		salt = resource.getString("salt");
	}
	
	private final static String SQL_LOGIN = "SELECT user.login, user.password, user.name, 'user' AS role "
			+ "FROM provider.user " + "WHERE (login =? AND password=MD5(?)) " + "UNION ALL "
			+ "SELECT administrator.login, administrator.password, 'Administrator' AS name, 'admin' AS role "
			+ "FROM provider.administrator " + "WHERE (login =? AND password=MD5(?));";
	private final static String SQL_NEW_USER = "INSERT INTO user (`login`, `password`, `name`, `email`) VALUES ('";
	

	
	

	@Override
	public User signIn(String login, String password) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_LOGIN);
			statement.setString(1, login);
			statement.setString(2, password + salt);
			statement.setString(3, login);
			statement.setString(4, password + salt);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String log = resultSet.getString("login");
				String pass = resultSet.getString("password");
				String name = resultSet.getString("name");
				String role = resultSet.getString("role");
				user = new User(log, pass, name, role);
			}

		} catch (SQLException e) {
			throw new DAOException("Cannot read user. ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
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
		return user;
	}

	@Override
	public boolean registration(User newUser) throws DAOException {
		boolean result = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String sql = SQL_NEW_USER + newUser.getLogin() + "', MD5('" + newUser.getPassword() + salt + "'), '" + newUser.getName() + "', '" + newUser.getEmail() + "');";
			System.out.println("Здесь" + sql);
			int resultAdd = statement.executeUpdate(sql);
			if (resultAdd != 0) {
				result = true;
			}	
		} catch (SQLException e) {
			throw new DAOException("Cannot add new user. ", e);
		}catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		}finally {
			if (statement != null) {
				try {
					statement.close();
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					//сообщение для логера
				}
			}
			connectionPool.freeConnection(connection);
		}
		
		return result;
	}


}
