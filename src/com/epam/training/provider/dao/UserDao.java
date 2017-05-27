package com.epam.training.provider.dao;

import com.epam.training.provider.bean.User;

public interface UserDao {
	
	// create
	public User read(String login, String password) throws UserDaoException;
	// update
	// delete
	
}
