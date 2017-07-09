package com.epam.training.provider.service.impl;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.dao.TransactionDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
import com.epam.training.provider.service.TransactionService;
import com.epam.training.provider.service.exception.ServiceException;

public class TransactionServiceImpl implements TransactionService {

	@Override
	public List<Transaction> listTransactionWithParameters(HashMap<String, String> parameters) throws ServiceException {
		if (parameters.isEmpty()) {
			throw new ServiceException("Parameters in listTransactionWithParameters weren't transferred!");
		}
		List<Transaction> transactions = null;
		
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		TransactionDAO dao = daoObjectFactory.getTransactionDAO();
		try {
			transactions = dao.searchWithParameters(parameters);
		} catch (DAOException e) {
			throw new ServiceException("Search of transactions with parameters wasn't executed!", e);
		}

		return transactions;
	}

	@Override
	public void addTransaction(Transaction transaction) throws ServiceException {
		if (transaction == null) {
			throw new ServiceException("Transaction in addTransaction wasn't transferred!");
		}
				
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		TransactionDAO dao = daoObjectFactory.getTransactionDAO();
		try {
			dao.addNew(transaction);
		} catch (DAOException e) {
			throw new ServiceException("Add of transaction wasn't executed!", e);
		}

		
	}

}
