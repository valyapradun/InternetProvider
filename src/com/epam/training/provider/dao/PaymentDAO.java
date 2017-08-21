package com.epam.training.provider.dao;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.dao.exception.DAOException;
/**
 * Interface of data access object for the operations with a payment.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface PaymentDAO {
	/**
	 * Method for searching with parameters of the payments.
	 * 
	 * @param parameters - {@link Map}
	 * @return List of the payments - {@link List}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public List<Payment> searchWithParameters(Map<String, String> parameters) throws DAOException;
	
	
	/**
	 * Method-transaction for adding a new payment and updating the balance of user.
	 * 
	 * @param payment - {@link Payment}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void addNew(Payment payment) throws DAOException;
	
	
	
	/**
	 * Method-transaction for buying a new tariff and updating the balance of user.
	 * 
	 * @param payment - {@link Payment}
	 * @param idTariff - {@link Tariff#id}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void buyNewTariff(Payment payment, int idTariff) throws DAOException;
	
}
