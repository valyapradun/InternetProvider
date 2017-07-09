package com.epam.training.provider.dao.factory;

import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.TransactionDAO;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.impl.TariffDAOImpl;
import com.epam.training.provider.dao.impl.TransactionDAOImpl;
import com.epam.training.provider.dao.impl.UserDAOImpl;


public final class DAOFactory {
	private static final DAOFactory instance = new DAOFactory();

	private final UserDAO userImpl = new UserDAOImpl();
	private final TariffDAO tariffImpl = new TariffDAOImpl();
	private final TransactionDAO transactionImpl = new TransactionDAOImpl();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return instance;
	}

	public UserDAO getUserDAO() {
		return userImpl;
	}

	public TariffDAO getTariffDAO() {
		return tariffImpl;
	}
	
	public TransactionDAO getTransactionDAO() {
		return transactionImpl;
	}

}
