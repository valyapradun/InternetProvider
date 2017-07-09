package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;

public class UserDAOImpl implements UserDAO {
	private static ConnectionPool connectionPool;
	private static String salt = ResourceBundle.getBundle("config").getString("salt");

	private final static String SQL_LOGIN = "SELECT user.id, user.login, user.password, user.name, 'user' AS role FROM provider.user WHERE (login =? AND password=MD5(?)) UNION ALL SELECT administrator.id, administrator.login, administrator.password, 'Administrator' AS name, 'admin' AS role FROM provider.administrator WHERE (login =? AND password=MD5(?))";
	private final static String SQL_UNIQUE_LOGIN = "SELECT count(user.login) AS count FROM provider.user WHERE user.login = ?";
	private final static String SQL_UNIQUE_EMAIL = "SELECT count(user.email) AS count FROM provider.user WHERE user.email = ?";
	private final static String SQL_NEW_USER = "INSERT INTO user (`login`, `password`, `name`, `email`) VALUES (?, MD5(?), ?, ?)";
	private final static String SQL_ALL_USERS = "SELECT user.id, user.login, user.name, user.email, user.balance, user.traffic_used FROM provider.user";
	private final static String SQL_ONE_USER = "SELECT user.id, user.login, user.name, user.email, user.balance, user.traffic_used FROM provider.user WHERE user.login = ?";

	private final static String USER_ID = "id";
	private final static String USER_LOGIN = "login";
	private final static String USER_PASSWORD = "password";
	private final static String USER_NAME = "name";
	private final static String USER_ROLE = "role";
	private final static String USER_EMAIL = "email";
	private final static String USER_BALANCE = "balance";
	private final static String USER_TRAFFIC = "traffic_used";
	private final static String USER_COUNT = "count";

	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException("Don't create Pool Connection!", e);
		}
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
				int id = resultSet.getInt(USER_ID);
				user = new User(id, log, pass, name, role);
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
		if (newUser == null) {
			throw new DAOException("The user is equal to null!");
		}

		Connection connection = null;
		PreparedStatement statement = null;

		// Statement statement = null;
		try {
			connection = connectionPool.takeConnection();

			statement = connection.prepareStatement(SQL_NEW_USER);
			statement.setString(1, newUser.getLogin());
			statement.setString(2, newUser.getPassword() + salt);
			statement.setString(3, newUser.getName());
			statement.setString(4, newUser.getEmail());

			statement.executeUpdate();

		} catch (SQLException e) {
			throw new DAOException("Cannot add new user. ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} finally {
			try {
				statement.close();
			} catch (SQLException e) {
				// logger.log(Level.ERROR, "Statement isn't closed.");
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
				count = resultSet.getInt(USER_COUNT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot check uniqueness of login! ", e);
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
		return count;
	}

	@Override
	public int uniqueEmail(String email) throws DAOException {
		if (email == null) {
			throw new DAOException("The email is equal to null!");
		}

		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_UNIQUE_EMAIL);
			statement.setString(1, email);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(USER_COUNT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot check uniqueness of email! ", e);
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
		return count;
	}

	@Override
	public List<User> searchWithParameters() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		try {
			connection = connectionPool.takeConnection();

			statement = connection.createStatement();
			String where = "";
			resultSet = statement.executeQuery(SQL_ALL_USERS + where);
			while (resultSet.next()) {
				int id = resultSet.getInt(USER_ID);
				String login = resultSet.getString(USER_LOGIN);
				String name = resultSet.getString(USER_NAME);
				String email = resultSet.getString(USER_EMAIL);
				double balance = resultSet.getDouble(USER_BALANCE);
				double traffic = resultSet.getDouble(USER_TRAFFIC);
				User user = new User(id, login, name, email, balance, traffic);
				users.add(user);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot display all users! ", e);
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

		return users;
	}

	@Override
	public User searchByLogin(String loginUser) throws DAOException {
		if (loginUser == null) {
			throw new DAOException("Login of user is equal to null!");
		}
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ONE_USER);
			statement.setString(1, loginUser);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int id = resultSet.getInt(USER_ID);
				String login = resultSet.getString(USER_LOGIN);
				String name = resultSet.getString(USER_NAME);
				String email = resultSet.getString(USER_EMAIL);
				double balance = resultSet.getDouble(USER_BALANCE);
				double traffic = resultSet.getDouble(USER_TRAFFIC);
				user = new User(id, login, name, email, balance, traffic);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot search this user! ", e);
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

}
