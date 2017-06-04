package com.epam.training.provider.dao;

import com.epam.training.provider.bean.User;

public interface UserDao {
	
	public boolean registration(User newUser) throws UserDaoException;
	public User signIn(String login, String password) throws UserDaoException;
	// update
	// delete
	
}
