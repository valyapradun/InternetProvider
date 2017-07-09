package com.epam.training.provider.dao;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Transaction;
import com.epam.training.provider.dao.exception.DAOException;

public interface TransactionDAO {
	public List<Transaction> searchWithParameters(HashMap<String, String> parameters) throws DAOException;
	public void addNew(Transaction transaction) throws DAOException;
}
