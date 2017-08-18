package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.connectionPool.ConnectionPool;
import com.epam.training.provider.dao.connectionPool.ConnectionPoolException;
import com.epam.training.provider.dao.exception.DAOException;

public class UserDAOImpl implements UserDAO {
	private final static Logger logger = LogManager.getLogger(UserDAOImpl.class.getName());
	private static String salt = ResourceBundle.getBundle("config").getString("salt");

	private final static String SQL_LOGIN = "SELECT user.id, user.login, user.password, user.name, 'user' AS role FROM provider.user WHERE (login =? AND password=MD5(?)) UNION ALL SELECT administrator.id, administrator.login, administrator.password, 'Administrator' AS name, 'admin' AS role FROM provider.administrator WHERE (login =? AND password=MD5(?))";
	private final static String SQL_UNIQUE_LOGIN = "SELECT count(user.login) AS count FROM provider.user WHERE user.login = ?";
	private final static String SQL_UNIQUE_EMAIL = "SELECT count(user.email) AS count FROM provider.user WHERE user.email = ?";
	private final static String SQL_NEW_USER = "INSERT INTO user (`login`, `password`, `name`, `email`) VALUES (?, MD5(?), ?, ?)";
	private final static String SQL_ALL_USERS = "SELECT user.id, user.login, user.name, user.email, user.balance, user.traffic_used FROM provider.user";
	private final static String SQL_ONE_USER = "SELECT user.id, user.login, user.name, user.email, user.balance, user.traffic_used FROM provider.user WHERE user.id = ?";
	private final static String SQL_UNIQUE_TARIFF = "SELECT count(user_to_tariff.tariff_id) AS count FROM provider.user_to_tariff WHERE user_to_tariff.user_id = ? AND user_to_tariff.end IS NULL";
	private final static String SQL_UNIQUE_BAN = "SELECT count(ban.user_id) AS count FROM provider.ban WHERE ban.user_id = ? AND ban.end_date IS NULL";
	private final static String SQL_ACTIVE_TARIFF = "SELECT tariff.name FROM provider.tariff JOIN provider.user_to_tariff ON tariff.id = user_to_tariff.tariff_id WHERE user_to_tariff.user_id = ? AND user_to_tariff.end IS NULL";
	private final static String SQL_DELETE_USER = "DELETE FROM provider.user WHERE user.id = ?";		
	private final static String SQL_NEGATIVE_BALANCE = "SELECT user.id, user.login, user.name, user.email, user.balance, user.traffic_used FROM provider.user WHERE user.balance < 0";
	private final static String SQL_USERS_WITH_UNLIM_MORE_30_DAYS = "SELECT user_to_tariff.id AS contract_id, user_to_tariff.user_id AS id, user_to_tariff.tariff_id, tariff.name, tariff.price FROM provider.user_to_tariff JOIN provider.tariff ON user_to_tariff.tariff_id = tariff.id WHERE tariff.tariff_type_id = 1 AND user_to_tariff.end is NULL AND user_to_tariff.begin <= CURDATE() - INTERVAL 30 DAY";
	private final static String SQL_CONTRACT_USER = "SELECT user_to_tariff.id AS contract_id FROM provider.user_to_tariff WHERE user_to_tariff.end is NULL AND user_to_tariff.user_id = ?";
	
	private final static String USER_ID = "id";
	private final static String USER_LOGIN = "login";
	private final static String USER_PASSWORD = "password";
	private final static String USER_NAME = "name";
	private final static String USER_ROLE = "role";
	private final static String USER_EMAIL = "email";
	private final static String USER_BALANCE = "balance";
	private final static String USER_TRAFFIC = "traffic_used";
	private final static String USER_COUNT = "count";
	private final static String TARIFF_ID = "tariff_id";
	private final static String TARIFF_NAME = "name";
	private final static String TARIFF_PRICE = "price";
	private final static String NUMBER_CONTRACT = "contract_id";
	
	private final static ConnectionPool connectionPool;
	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			logger.log(Level.ERROR, "Don't create Pool Connection!" + e);
			throw new RuntimeException("Don't create Pool Connection!", e);
		}
	}

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
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}
		return user;
	}

	@Override
	public void registration(User newUser) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

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
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

	}

	@Override
	public int uniqueLogin(String login) throws DAOException {
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
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}
		return count;
	}

	@Override
	public int uniqueEmail(String email) throws DAOException {
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
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
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
			resultSet = statement.executeQuery(SQL_ALL_USERS);
			
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
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

		return users;
	}

	
	@Override
	public User searchById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		User user = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ONE_USER);
			statement.setInt(1, id);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
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
				 logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				 logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

		return user;
	}

	@Override
	public int countActiveTariffs(int userID) throws DAOException {
		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_UNIQUE_TARIFF);
			statement.setInt(1, userID);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(USER_COUNT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Сan't count active tariffsQQQQ! ", e);
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
		return count;
	}

	@Override
	public String searchActiveTariff(int userID) throws DAOException {
		String tariffName = "";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ACTIVE_TARIFF);
			statement.setInt(1, userID);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				tariffName = resultSet.getString(TARIFF_NAME);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Сan't search the active tariff! ", e);
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
		return tariffName;
	}
	
	@Override
	public void delete(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_DELETE_USER);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot delete the user. " + e.getMessage(), e);
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
	public List<User> negativeBalance() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_NEGATIVE_BALANCE);
			
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
			throw new DAOException("Cannot display users with negative balance! ", e);
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

		return users;
	}

	@Override
	public int countActiveBan(int userID) throws DAOException {
		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_UNIQUE_BAN);
			statement.setInt(1, userID);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(USER_COUNT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Сan't count active bans! ", e);
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
		return count;
	}

	@Override
	public List<User> usersWithUnlimMore30Days() throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<User> users = new ArrayList<User>();
		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_USERS_WITH_UNLIM_MORE_30_DAYS);
			
			while (resultSet.next()) {
				int id = resultSet.getInt(USER_ID);
				int tariffID = resultSet.getInt(TARIFF_ID);
				String tariffName = resultSet.getString(TARIFF_NAME);
				double tariffPrice = resultSet.getDouble(TARIFF_PRICE);
				int numberContract = resultSet.getInt(NUMBER_CONTRACT);
				
				User user = new User();
				Tariff tariff = new Tariff();
				user.setId(id);
				tariff.setId(tariffID);
				tariff.setPrice(tariffPrice);
				tariff.setName(tariffName);
				user.setTariff(tariff);
				user.setNumberContract(numberContract);
				users.add(user);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot display users! ", e);
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

		return users;
	}

	@Override
	public int searchContractId(int userID) throws DAOException {
		int contract = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_CONTRACT_USER);
			statement.setInt(1, userID);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				contract = resultSet.getInt(NUMBER_CONTRACT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Сan't search contract of the user! ", e);
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
		
		return contract;
	}

}
