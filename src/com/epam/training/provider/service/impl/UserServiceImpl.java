package com.epam.training.provider.service.impl;

import java.util.List;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
//import com.epam.training.provider.dao.impl.UserDAOImpl;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

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
	public void registration(User newUser) throws ServiceException, ValidateException {
		String errors = validate(newUser);
		if (errors.isEmpty()) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				UserDAO dao = daoObjectFactory.getUserDAO();
				dao.registration(newUser);
			} catch (DAOException e) {
				throw new ServiceException("Registration wasn't executed! ", e);
			}
		} else {
			throw new ValidateException(errors);
		}

	}

	public String validate(User newUser) throws ServiceException {

		if (newUser == null) {
			throw new ServiceException("The user is equal to null! ");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(checkUniqueLogin(newUser.getLogin()));
		buffer.append(checkUniqueEmail(newUser.getEmail()));
		buffer.append(Validate.checkLatinSymbol(newUser.getLogin()));
		buffer.append(Validate.checkDifficultPassword(newUser.getPassword()));
		buffer.append(Validate.checkValidEmail(newUser.getEmail()));

		return buffer.toString();

	}

	@Override
	public String checkUniqueLogin(String login) throws ServiceException {
		String result = "";
		if (login == null) {
			throw new ServiceException("The login is equal to null!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			int count = dao.uniqueLogin(login);
			if (count > 0) {
				result = "- Such login already exists! ";
			}
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of login wasn't executed! ", e);
		}
		return result;
	}

	@Override
	public String checkUniqueEmail(String email) throws ServiceException {
		String result = "";
		if (email == null) {
			throw new ServiceException("The email is equal to null!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			int count = dao.uniqueEmail(email);
			if (count > 0) {
				result = "- Such email already exists! ";
			}
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of email wasn't executed! ", e);
		}
		return result;
	}

	@Override
	public List<User> listUsersWithParameters() throws ServiceException {
		List<User> users = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			users = dao.searchWithParameters();
		} catch (DAOException e) {
			throw new ServiceException("Search of users wasn't executed! ", e);
		}
		return users;
	}

	@Override
	public User userByLogin(String login) throws ServiceException {
		if (login == null) {
			throw new ServiceException("The login is equal to null!");
		}
		
		User user = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			user = dao.searchByLogin(login);
		} catch (DAOException e) {
			throw new ServiceException("Search of users wasn't executed! ", e);
		}
		return user;
	}

}
