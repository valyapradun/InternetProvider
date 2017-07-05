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
		if ((login == null) || (password == null)) {
			throw new ServiceException("The login or password is equal to null!");
		}

		User user = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			user = dao.signIn(login, password);
		} catch (DAOException e) {
			throw new ServiceException("Authorization wasn't executed! ", e);
		}

		return user;
	}

	@Override
	public void registration(User newUser) throws ServiceException {
		validate(newUser);

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			dao.registration(newUser);
		} catch (DAOException e) {
			throw new ServiceException("Registration wasn't executed! ", e);
		}

	}

	public void validate(User newUser) throws ServiceException {
		if (newUser == null) {
			throw new ServiceException("The user is equal to null! ");
		}

		if (uniqueLogin(newUser.getLogin()) > 0) {
			throw new ServiceException("Such login already exists! ");
		}

	}

	@Override
	public int uniqueLogin(String login) throws ServiceException {
		int count = 0;
		if (login == null) {
			throw new ServiceException("The login is equal to null!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			count = dao.uniqueLogin(login);
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of login wasn't executed! ", e);
		}
		return count;
	}

}
