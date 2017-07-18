package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;

import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class TariffDAOImpl implements TariffDAO {
	private final static Logger logger = LogManager.getLogger(TariffDAOImpl.class.getName());
	
	private final static String SQL_ALL_TARIFFS = "SELECT tariff.id, tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type, tariff.picture FROM provider.tariff JOIN provider.tariff_type ON tariff_type.id = tariff.tariff_type_id";
	private final static String SQL_ONE_TARIFF = "SELECT tariff.id, tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type, tariff.picture FROM provider.tariff JOIN provider.tariff_type ON tariff_type.id = tariff.tariff_type_id WHERE tariff.id = ?";
	private final static String SQL_EDIT_TARIFF = "UPDATE provider.tariff SET tariff.name = ?, tariff.price = ?, tariff.size = ?, tariff.speed = ?, tariff.picture = ?, tariff.tariff_type_id = ? WHERE tariff.id = ?";
	private final static String SQL_NEW_TARIFF = "INSERT INTO provider.tariff (name, price, size, speed, picture, tariff_type_id) VALUES (?, ?, ?, ?, ?, ?)";
	private final static String SQL_DELETE_TARIFF = "DELETE FROM provider.tariff WHERE tariff.id = ?";
	
	private final static String TARIFF_ID = "id";
	private final static String TARIFF_NAME = "name";
	private final static String TARIFF_TYPE = "type";
	private final static String TARIFF_PRICE = "price";
	private final static String TARIFF_SIZE = "size";
	private final static String TARIFF_SPEED = "speed";
	private final static String TARIFF_PICTURE = "picture";
	
	private static ConnectionPool connectionPool;
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
		Statement statement = null;
		ResultSet resultSet = null;
		List<Tariff> tariffs = new ArrayList<Tariff>();

		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String where = "";

			if (!parameters.isEmpty() && (parameters.get(TARIFF_TYPE) != null)) {
				where = " WHERE tariff.tariff_type_id = " + parameters.get(TARIFF_TYPE);
			}

			resultSet = statement.executeQuery(SQL_ALL_TARIFFS + where);

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
		if (id <= 0) {
			throw new DAOException("ID of tariff is less or is equal to 0!");
		}
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
		if (tariff == null) {
			throw new DAOException("The tariff for editing is equal to null!");
		}

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
		if (tariff == null) {
			throw new DAOException("The tariff for adding is equal to null!");
		}

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
		if (id <= 0) {
			throw new DAOException("ID of tariff is less or is equal to 0!");
		}

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


}
