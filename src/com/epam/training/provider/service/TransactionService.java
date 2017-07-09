package com.epam.training.provider.service;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.service.exception.ServiceException;

public interface TransactionService {
	public List<Transaction> listTransactionWithParameters(HashMap<String, String> parameters) throws ServiceException;
	public void addTransaction(Transaction transaction) throws ServiceException;
}
