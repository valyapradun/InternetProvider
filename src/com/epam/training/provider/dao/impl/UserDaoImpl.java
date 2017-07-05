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

	private final static String SQL_LOGIN = "SELECT user.login, user.password, user.name, 'user' AS role FROM provider.user WHERE (login =? AND password=MD5(?)) UNION ALL SELECT administrator.login, administrator.password, 'Administrator' AS name, 'admin' AS role FROM provider.administrator WHERE (login =? AND password=MD5(?))";
	private final static String SQL_UNIQUE_LOGIN = "SELECT count(user.login) AS count FROM provider.user WHERE user.login = ?";
	
	
	private final static String SQL_NEW_USER = "INSERT INTO user (`login`, `password`, `name`, `email`) VALUES ('";

	
	
	
	
	private final static String USER_LOGIN = "login";
	private final static String USER_PASSWORD = "password";
	private final static String USER_NAME = "name";
	private final static String USER_ROLE = "role";

	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("Don't create Pool Connection!", e);
		}
		ResourceBundle resource = ResourceBundle.getBundle("config");
		salt = resource.getString("salt");
	}

	@Override
	public User signIn(String login, String password) throws DAOException {
		if ((login == null) || (password == null)) {
			throw new DAOException("The login or password is equal to null!");
		}

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
				String log = resultSet.getString(USER_LOGIN);
				String pass = resultSet.getString(USER_PASSWORD);
				String name = resultSet.getString(USER_NAME);
				String role = resultSet.getString(USER_ROLE);
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
	public void registration(User newUser) throws DAOException {

		Connection connection = null;
		Statement statement = null;
		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String sql = SQL_NEW_USER + newUser.getLogin() + "', MD5('" + newUser.getPassword() + salt + "'), '"
					+ newUser.getName() + "', '" + newUser.getEmail() + "');";
			System.out.println("�����" + sql);
			int resultAdd = statement.executeUpdate(sql);
			if (resultAdd != 0) {
				
			}
		} catch (SQLException e) {
			throw new DAOException("Cannot add new user. ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} finally {
			if (statement != null) {
				try {
					statement.close();
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					// ��������� ��� ������
				}
			}
			connectionPool.freeConnection(connection);
		}

	}

	@Override
	public int uniqueLogin(String login) throws DAOException {
		if (login == null) {
			throw new DAOException("The login is equal to null!");
		}

		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_UNIQUE_LOGIN);
			statement.setString(1, login);
			
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt("count");	
			}
			
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot check uniqueness of login! ", e);
		}
		return count;
	}

}
