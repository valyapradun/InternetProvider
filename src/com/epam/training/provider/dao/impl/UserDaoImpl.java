package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDao;
import com.epam.training.provider.dao.UserDaoException;

public class UserDaoImpl implements UserDao {
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
	public User signIn(String login, String password) throws UserDaoException {
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
			System.out.println(statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				String lg = resultSet.getString("login");
				String pw = resultSet.getString("password");
				String name = resultSet.getString("name");
				String role = resultSet.getString("role");
				user = new User(lg, pw, name, role);
			}

		} catch (SQLException e) {
			throw new UserDaoException("Cannot read user. ", e);
		} catch (ConnectionPoolException e) {
			throw new UserDaoException("ConnectionPoolException: ", e);
		} finally {
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
		return user;
	}

	@Override
	public boolean registration(User newUser) throws UserDaoException {
		boolean result = false;
		Connection connection = null;
		Statement statement = null;
		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String sql = SQL_NEW_USER + newUser.getLogin() + "', MD5('" + newUser.getPassword() + salt + "'), '" + newUser.getName() + "', '" + newUser.getEmail() + "');";
			System.out.println(sql);
			int resultAdd = statement.executeUpdate(sql);
			if (resultAdd != 0) {
				result = true;
			}	
		} catch (SQLException e) {
			throw new UserDaoException("Cannot add new user. ", e);
		}catch (ConnectionPoolException e) {
			throw new UserDaoException("ConnectionPoolException: ", e);
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
