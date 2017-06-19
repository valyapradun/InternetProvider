package com.epam.training.provider.service.impl;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
//import com.epam.training.provider.dao.impl.UserDAOImpl;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;

public class UserServiceImpl implements UserService {


	@Override
	public User authorize(String login, String password) throws ServiceException {
		if ((login == null) && (password == null)) {
			throw new ServiceException("The login or the password are null!");
		}

		User user = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			
		//	UserDAO dao = new UserDAOImpl();
				
			
			user = dao.signIn(login, password);
		} catch (DAOException e) {
			throw new ServiceException("Authorization wasn't executed! ", e);
		}

		return user;
	}

	@Override
	public boolean registration(User newUser) throws ServiceException {
		boolean result = false;
		if (newUser != null) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				UserDAO dao = daoObjectFactory.getUserDAO();
				result = dao.registration(newUser);
			} catch (DAOException e) {
				throw new ServiceException("Registration wasn't executed! ", e);
			}
		}
		return result;
	}

}
