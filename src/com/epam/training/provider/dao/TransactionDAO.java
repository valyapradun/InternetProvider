package com.epam.training.provider.dao;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.dao.exception.DAOException;

public interface TransactionDAO {
	public List<Payment> searchWithParameters(HashMap<String, String> parameters) throws DAOException;
	public void addNew(Payment transaction) throws DAOException;
}
