package com.epam.training.provider.dao;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.exception.DAOException;

public interface UserDAO {
	public void registration(User newUser) throws DAOException;
	public User signIn(String login, String password) throws DAOException;
	public int uniqueLogin(String login) throws DAOException;
	// update
		// delete


}
