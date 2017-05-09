package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDao;

public class UserDaoImpl implements UserDao{

	@Override
	public User read(String login, String password) {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/provider?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
			st = con.prepareStatement("SELECT user.login, user.password, user.role_id AS role "
					+ "FROM provider.user "
					+ "WHERE (login =? AND password=?) "
					+ "UNION "
					+ "SELECT administrator.login, administrator.password, administrator.role_id AS role "
					+ "FROM provider.administrator "
					+ "WHERE (login =? AND password=?);");
			st.setString(1, login);
			st.setString(2, password);
			st.setString(3, login);
			st.setString(4, password);
			rs = st.executeQuery();

			while (rs.next()) {
				String lg = rs.getString("login");
				String pw = rs.getString("password");
				int role = rs.getInt("role");
				user = new User(lg, pw, role);
			}

			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} finally {
				if (st != null) {
					try {
						st.close();
						if (con != null) {
							con.close();
						}
					} catch (SQLException e) {
					}
				}
			}
			return user;
	}

}
