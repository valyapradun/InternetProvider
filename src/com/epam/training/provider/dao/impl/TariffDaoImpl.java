package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;

import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.connectionPool.ConnectionPool;
import com.epam.training.provider.dao.connectionPool.ConnectionPoolException;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class TariffDAOImpl implements TariffDAO {
	private final static Logger logger = LogManager.getLogger(TariffDAOImpl.class.getName());
	
	private final static String SQL_ALL_TARIFFS = "SELECT tariff.id, tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type, tariff.picture FROM provider.tariff JOIN provider.tariff_type ON tariff_type.id = tariff.tariff_type_id";
	private final static String SQL_ONE_TARIFF = "SELECT tariff.id, tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type, tariff.picture FROM provider.tariff JOIN provider.tariff_type ON tariff_type.id = tariff.tariff_type_id WHERE tariff.id = ?";
	private final static String SQL_EDIT_TARIFF = "UPDATE provider.tariff SET tariff.name = ?, tariff.price = ?, tariff.size = ?, tariff.speed = ?, tariff.picture = ?, tariff.tariff_type_id = ? WHERE tariff.id = ?";
	private final static String SQL_NEW_TARIFF = "INSERT INTO provider.tariff (name, price, size, speed, picture, tariff_type_id) VALUES (?, ?, ?, ?, ?, ?)";
	private final static String SQL_DELETE_TARIFF = "DELETE FROM provider.tariff WHERE tariff.id = ?";
	private final static String SQL_UNIQUE_TARIFF = "SELECT count(tariff.name) AS count FROM provider.tariff WHERE tariff.name = ? AND tariff.id <> ?";
	private final static String SQL_FOR_WHERE_TARIFF_TYPE = " WHERE tariff.tariff_type_id = ?";
	private final static String SQL_END_TARIFF = "UPDATE provider.user_to_tariff SET end = CURDATE() WHERE user_to_tariff.id = ?";
	
	private final static String TARIFF_ID = "id";
	private final static String TARIFF_NAME = "name";
	private final static String TARIFF_TYPE = "type";
	private final static String TARIFF_PRICE = "price";
	private final static String TARIFF_SIZE = "size";
	private final static String TARIFF_SPEED = "speed";
	private final static String TARIFF_PICTURE = "picture";
	private final static String TARIFF_COUNT = "count";
	
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
	public List<Tariff> searchWithParameters(Map<String, String> parameters) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		List<Tariff> tariffs = new ArrayList<Tariff>();

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ALL_TARIFFS);
			
			if (parameters.get(TARIFF_TYPE) != null) {
				statement = connection.prepareStatement(SQL_ALL_TARIFFS + SQL_FOR_WHERE_TARIFF_TYPE);
				statement.setInt(1, Integer.parseInt(parameters.get(TARIFF_TYPE)));
			}

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int id = resultSet.getInt(TARIFF_ID);
				String name = resultSet.getString(TARIFF_NAME);
				TariffType type = TariffType.valueOf(resultSet.getString(TARIFF_TYPE).toUpperCase());
				double price = resultSet.getDouble(TARIFF_PRICE);
				double size = resultSet.getDouble(TARIFF_SIZE);
				int speed = resultSet.getInt(TARIFF_SPEED);
				String picture = resultSet.getString(TARIFF_PICTURE);
				Tariff tariff = new Tariff(id, name, type, price, size, speed, picture);
				tariffs.add(tariff);
			}

		} catch (SQLException e) {
			throw new DAOException("Cannot read tariffs. ", e);
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
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

		return tariffs;
	}

	@Override
	public Tariff searchById(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		Tariff tariff = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_ONE_TARIFF);
			statement.setInt(1, id);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				id = resultSet.getInt(TARIFF_ID);
				String name = resultSet.getString(TARIFF_NAME);
				TariffType type = TariffType.valueOf(resultSet.getString(TARIFF_TYPE).toUpperCase());
				double price = resultSet.getDouble(TARIFF_PRICE);
				double size = resultSet.getDouble(TARIFF_SIZE);
				int speed = resultSet.getInt(TARIFF_SPEED);
				String picture = resultSet.getString(TARIFF_PICTURE);
				tariff = new Tariff(id, name, type, price, size, speed, picture);
			}
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot find the tariff. ", e);
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
		return tariff;
	}

	@Override
	public void edit(Tariff tariff) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_EDIT_TARIFF);
			statement.setString(1, tariff.getName());
			statement.setDouble(2, tariff.getPrice());
			statement.setDouble(3, tariff.getSize());
			statement.setInt(4, tariff.getSpeed());
			statement.setString(5, tariff.getPicture());
			statement.setInt(6, tariff.getType().ordinal() + 1);
			statement.setInt(7, tariff.getId());

			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot edit the tariff. ", e);
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
	public void addNew(Tariff tariff) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			
			statement = connection.prepareStatement(SQL_NEW_TARIFF);
			statement.setString(1, tariff.getName());
			statement.setDouble(2, tariff.getPrice());
			statement.setDouble(3, tariff.getSize());
			statement.setInt(4, tariff.getSpeed());
			statement.setString(5, tariff.getPicture());
			statement.setInt(6, tariff.getType().ordinal() + 1);

			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot add the tariff. ", e);
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
	public void delete(int id) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_DELETE_TARIFF);
			statement.setInt(1, id);
			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot delete the tariff. ", e);
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
	public void endTariff(int idContract) throws DAOException {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_END_TARIFF);
			statement.setInt(1, idContract);
			statement.executeUpdate();
		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException. ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot end to tariff. ", e);
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
	public int uniqueTariff(Tariff tariff) throws DAOException {
		int count = 0;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = connectionPool.takeConnection();
			statement = connection.prepareStatement(SQL_UNIQUE_TARIFF);
			statement.setString(1, tariff.getName());
			statement.setInt(2, tariff.getId());

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(TARIFF_COUNT);
			}

		} catch (ConnectionPoolException e) {
			throw new DAOException("ConnectionPoolException: ", e);
		} catch (SQLException e) {
			throw new DAOException("Cannot check uniqueness of tariff's name! ", e);
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

}
