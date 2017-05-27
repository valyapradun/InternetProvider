package com.epam.training.provider.service.impl;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDao;
import com.epam.training.provider.dao.UserDaoException;
import com.epam.training.provider.dao.impl.UserDaoImpl;
import com.epam.training.provider.service.UserService;

public class UserServiceImpl implements UserService{
	private UserDao dao;
	{
		dao = new UserDaoImpl();
	}

	@Override
	public User authorize(String login, String password) {
		User user = null;
		try {
			user = dao.read(login, password);
		} catch (UserDaoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

}
