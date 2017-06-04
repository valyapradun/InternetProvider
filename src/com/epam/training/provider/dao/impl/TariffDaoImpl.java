package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.TariffType;

import com.epam.training.provider.dao.TariffDao;
import com.epam.training.provider.dao.TariffDaoException;

public class TariffDaoImpl implements TariffDao {
/*	private ConnectionPool connectionPool;
	private final static String SQL_ALL_TARIFFS = "SELECT * FROM provider.tariff;";
//	SELECT tariff.name, tariff.price, tariff.size, tariff.speed, tariff_type.type 
//	FROM provider.tariff
//	JOIN provider.tariff_type
//	ON tariff_type.id = tariff.tariff_type_id;

	static {
		
	    ConnectionPool connectionPool = ConnectionPool.getInstance();
		try {
			connectionPool.createPool();
		} catch (ConnectionPoolException e) {
			throw new RuntimeException();
		}
}
	
	*/
	@Override
	public List<Tariff> viewAll() throws TariffDaoException {
/*		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		List<Tariff> tariffs = new ArrayList<Tariff>();
	//	ConnectionPool connectionPool = new ConnectionPool();
		
		try {
	//		connectionPool.createPool();
			connection = connectionPool.takeConnection();
//			statement = connection.createStatement();
//			resultSet = statement.executeQuery(SQL_ALL_TARIFFS);
//
//			while (resultSet.next()) {
//				String name = resultSet.getString("name");
//				TariffType type = ((TariffType) value).resultSet.getInt("type");
//				double price = resultSet.getDouble("price");
//				double size = resultSet.getDouble("size");
//				int speed = resultSet.getInt("speed");
//				Tariff tariff = new Tariff(name, type, price, size, speed);
//				tariffs.add(tariff);
//			}
			
			
			
			
			
			
			
		} catch (ConnectionPoolException e) {
			throw new TariffDaoException("ConnectionPoolException: ", e);
		}
		
		
		*/

		return null;
	}

}
