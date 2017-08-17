package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

/**
 * Interface of business-logic for the payments operations.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface PaymentService {
	/**
	 * Method for searching of payments by criteria.
	 * 
	 * @param criteria - {@link Map}
	 * @return List of payments - {@link List}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors      
	 */
	public List<Payment> listPaymentsWithCriteria(Map<String, String> criteria) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for adding of payments.
	 * 
	 * @param payment {@link Payment} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	public void addPayment(Payment payment) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for buying of tariff.
	 * 
	 * <ol>
	 *   <li>To check an active tariff at the user</li>
	 *   <li>To find price of tariff</li>
	 *   <li>To check a balance at the user</li>
	 *   <li>To buy this tariff:
	 *    <ul>
	 *       <li>to add new row in the table 'user_to_tariff'</li>
	 *       <li>to add new payment (type - WITHDRAW)</li>
	 *       <li>to update the balance of user</li>
	 *     </ul>
	 *   </li>
	 * </ol>
	 * 
	 * @param userID {@link User#id}
	 * @param tariffID {@link Tariff#id}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors       
	 */
	public void buyTariff(int userID, int tariffID) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for prolonging of unlim-tariffs.
	 * 
	 * <ol>
	 *   <li>To choose all users who have UNLIM tariff with:
	 *     <ul>
	 *       <li>date of tariff's end is NULL</li>
	 *       <li>date of tariff's beginning more current date than 30 days</li>
	 *     </ul>
	 *   </li>
	 *   <li>For these users to change the date of tariff's on current date.</li>
	 *   <li>For these users to buy those tariffs again:
	 *    <ul>
	 *       <li>to add new row in the table 'user_to_tariff'</li>
	 *       <li>to add new payment (type - WITHDRAW)</li>
	 *       <li>to update the balance of user</li>
	 *     </ul>
	 *   </li>
	 * </ol>
	 * 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException         
	 */
	public void prolongUnlimTariffs() throws ServiceException;
	
}
