package com.epam.training.provider.service;

import com.epam.training.provider.bean.User;

public interface UserService {
	public User authorize(String login, String password);

}
