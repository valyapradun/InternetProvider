package com.epam.training.provider.dao;

import java.util.List;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.exception.DAOException;

public interface UserDAO {
	public void registration(User newUser) throws DAOException;
	public User signIn(String login, String password) throws DAOException;
	public int uniqueLogin(String login) throws DAOException;
	public int uniqueEmail(String email) throws DAOException;
	public List<User> searchWithParameters() throws DAOException;
	public User searchByLogin(String login) throws DAOException;
	// update
	// delete

}
