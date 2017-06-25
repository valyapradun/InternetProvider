package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;

import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.exception.DAORuntimeException;

public class TariffDAOImpl implements TariffDAO {
	private static ConnectionPool connectionPool;
	private final static String SQL_ALL_TARIFFS = "SELECT tariff.id, tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type, tariff.picture FROM provider.tariff JOIN provider.tariff_type ON tariff_type.id = tariff.tariff_type_id";

	static {
		try {
			connectionPool = ConnectionPool.getInstance();
		} catch (ConnectionPoolException e) {
			throw new DAORuntimeException("Don't create Pool Connection!", e);
		}
	}

	@Override
	public List<Tariff> searchWithParameters(HashMap<String, String> parameters) throws DAOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Tariff> tariffs = new ArrayList<Tariff>();

		try {
			connection = connectionPool.takeConnection();
			statement = connection.createStatement();
			String where = "";

			if (!parameters.isEmpty() && (parameters.get("type") != null)) {
				where = " WHERE tariff.tariff_type_id = " + parameters.get("type");
			}

			resultSet = statement.executeQuery(SQL_ALL_TARIFFS + where);

			while (resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				TariffType type = TariffType.valueOf(resultSet.getString("type").toUpperCase());
				double price = resultSet.getDouble("price");
				double size = resultSet.getDouble("size");
				int speed = resultSet.getInt("speed");
				String picture = resultSet.getString("picture");
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
				// logger.log(Level.ERROR, "ResultSet isn't closed.");
			}

			try {
				statement.close();
			} catch (SQLException e) {
				// logger.log(Level.ERROR, "Statement isn't closed.");
			}

			connectionPool.freeConnection(connection);
		}

		return tariffs;
	}

	@Override
	public Tariff searchById(int id) throws DAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
