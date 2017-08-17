package com.epam.training.provider.service;

import java.util.List;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
/**
 * Interface of business-logic for the operations of user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface UserService {
	/**
	 * Method for authorization of user.
	 * 
	 * @param login - {@link User#login}
	 * @param password - {@link User#password}
	 * @return user - {@link User}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors     
	 */
	public User authorize(String login, String password) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for registration of user.
	 * 
	 * @param newUser - {@link User}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors          
	 */
	public void registration(User newUser) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for checking of uniqueness of a login of user.
	 * Calculation of the count of users with current login.
	 * Throw ValidateException if count > 0. 
	 * 
	 * @param login - {@link User#login}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */
	public String checkUniqueLogin(String login) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for checking of uniqueness of a email of user.
	 * Calculation of the count of users with current email. 
	 * Throw ValidateException if count > 0.
	 * 
	 * @param email - {@link User#email}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors        
	 */
	public String checkUniqueEmail(String email) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for searching of all users.
	 * 
	 * @return list of users - {@link List}
	 * @throws ServiceException Exception from the DAO-level      
	 */
	public List<User> listUsersWithParameters() throws ServiceException;
	
	
	/**
	 * Method for searching of user by id.
	 * 
	 * @param id - {@link User#id}
	 * @return user - {@link User}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors     
	 */
	public User userById(int id) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for deleting of user by id.
	 * 
	 * @param id - {@link User#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors   
	 */
	public void deleteUser(int id) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for putting of bans.
	 * 
	 * @param adminId - {@link User#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors        
	 */
	public void putBan(int adminId) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for removing of ban.
	 * 
	 * @param adminId - {@link User#id} 
	 * @param userId - {@link User#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */
	public void removeBan(int adminId, int userId) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for ending of the active tariff of the current user.
	 * 
	 * @param userId - {@link User#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */	
	public void endTariff(int userId) throws ServiceException, ValidateException;

	
	/**
	 * Method for checking of negative balance at the current user.
	 * Return 'true' if balance at the current user < 0.
	 * 
	 * @param userID - {@link User#id} 
	 * @return true or false
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */	
	public boolean checkNegativeBalance(int userID) throws ValidateException, ServiceException;
	
	
	/**
	 * Method for checking of active ban at the current user.
	 * Calculation of the count of active ban. Return 'true' if count > 0.
	 * 
	 * @param userID - {@link User#id}
	 * @return true or false
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors       
	 */
	public boolean checkActiveBan(int userID) throws ValidateException, ServiceException;
	
	
	/**
	 * Method for checking of active tariff at the current user.
	 * Calculation of the count of active tariff. Return 'true' if count > 0.
	 * 
	 * @param userID - {@link User#id}
	 * @return true or false
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors       
	 */
	public boolean checkActiveTariff(int userID) throws ValidateException, ServiceException;
}
