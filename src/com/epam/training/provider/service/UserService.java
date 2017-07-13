package com.epam.training.provider.service;

import java.util.List;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

public interface UserService {
	
	public User authorize(String login, String password) throws ServiceException;
	public void registration(User newUser) throws ServiceException, ValidateException;
	public String checkUniqueLogin(String login) throws ServiceException;
	public String checkUniqueEmail(String email) throws ServiceException;
	public List<User> listUsersWithParameters() throws ServiceException;
	public User userById(int id) throws ServiceException;

}
