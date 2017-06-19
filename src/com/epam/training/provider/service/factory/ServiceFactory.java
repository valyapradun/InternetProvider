package com.epam.training.provider.service.factory;

import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.impl.TariffServiceImpl;
import com.epam.training.provider.service.impl.UserServiceImpl;

public final class ServiceFactory {
	private static final ServiceFactory instance = new ServiceFactory();

	private final UserService userService = new UserServiceImpl();
	private final TariffService tariffService = new TariffServiceImpl();

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public UserService getUserService() {
		return userService;
	}

	public TariffService getTariffService() {
		return tariffService;
	}
}
