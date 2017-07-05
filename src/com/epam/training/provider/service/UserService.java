package com.epam.training.provider.service;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.exception.ServiceException;

public interface UserService {
	public User authorize(String login, String password) throws ServiceException;
	public void registration(User newUser) throws ServiceException;
	public int uniqueLogin(String login) throws ServiceException;

}
