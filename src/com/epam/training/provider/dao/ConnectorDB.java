package com.epam.training.provider.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectorDB {
	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		ResourceBundle resource = ResourceBundle.getBundle("db");
		String driver = resource.getString("db.driver");
		String url = resource.getString("db.url");
		String user = resource.getString("db.user");
		String pass = resource.getString("db.password");
		Class.forName(driver);
		return DriverManager.getConnection(url, user, pass);
	}
}