package com.epam.training.provider.dao;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.dao.exception.DAOException;

public interface PaymentDAO {
	
	public List<Payment> searchWithParameters(Map<String, String> parameters) throws DAOException;
	public void addNew(Payment payment) throws DAOException;
	public void buyNewTariff(Payment payment, int idTarif) throws DAOException;
	
}
