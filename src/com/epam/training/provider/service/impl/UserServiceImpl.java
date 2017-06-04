package com.epam.training.provider.service.impl;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDao;
import com.epam.training.provider.dao.UserDaoException;
import com.epam.training.provider.dao.impl.UserDaoImpl;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.UserServiceException;

public class UserServiceImpl implements UserService{
	private UserDao dao;
	{
		dao = new UserDaoImpl();
	}

	@Override
	public User authorize(String login, String password) throws UserServiceException {
		User user = null;
		if((login != null) && (password != null)){
			try {
				user = dao.signIn(login, password);
			} catch (UserDaoException e) {
				throw new UserServiceException("Authorization wasn't executed! ", e);
			}	
		}
		return user;
	}

	@Override
	public boolean registration(User newUser) throws UserServiceException {
		boolean result = false;
		if (newUser != null){
			try {
				result = dao.registration(newUser);
			} catch (UserDaoException e) {
				throw new UserServiceException("Registration wasn't executed! ", e);
			}
		}
		return result;
	}

}
