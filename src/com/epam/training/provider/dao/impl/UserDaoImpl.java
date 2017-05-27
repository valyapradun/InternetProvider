package com.epam.training.provider.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.ConnectionPool;
import com.epam.training.provider.dao.ConnectionPoolException;
import com.epam.training.provider.dao.UserDao;
import com.epam.training.provider.dao.UserDaoException;

public class UserDaoImpl implements UserDao{
	private final static String SQL_LOGIN = "SELECT user.login, user.password, 'user' AS role "
			+ "FROM provider.user "
			+ "WHERE (login =? AND password=?) "
			+ "UNION ALL "
			+ "SELECT administrator.login, administrator.password, 'admin' AS role "
			+ "FROM provider.administrator "
			+ "WHERE (login =? AND password=?);";
	
	
	

	@Override
	public User read(String login, String password) throws UserDaoException {
		Connection con = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		User user = null;
		ConnectionPool cp = new ConnectionPool();
		
		try {
			cp.createPool();
		    con = cp.takeConnection();
			
		//	con = ConnectorDB.getConnection();
		//	con = DriverManager.getConnection("jdbc:mysql://localhost/provider?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "123456");
			st = con.prepareStatement(SQL_LOGIN);
			st.setString(1, login);
			st.setString(2, password);
			st.setString(3, login);
			st.setString(4, password);
			rs = st.executeQuery();

			while (rs.next()) {
				String lg = rs.getString("login");
				String pw = rs.getString("password");
				String role = rs.getString("role");
				user = new User(lg, pw, role);
			}

			} catch (SQLException e) {
				throw new UserDaoException("ConnectionPoolException: ", e);
			}  catch (ConnectionPoolException e) {
				throw new UserDaoException("ConnectionPoolException: ", e);
			}
		finally {
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
