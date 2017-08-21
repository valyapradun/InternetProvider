package com.epam.training.provider.dao;

import java.util.List;

import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.exception.DAOException;
/**
 * Interface of data access object for the operations with a user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface UserDAO {
	
	/**
	 * Method for registration of the new user.
	 * 
	 * @param newUser - {@link User}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void registration(User newUser) throws DAOException;
	
	
	/**
	 * Method for authorization of the user.
	 * 
	 * @param login - {@link String}
	 * @param password - {@link String}
	 * @return User - {@link User}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public User signIn(String login, String password) throws DAOException;
	
	
	/**
	 * Method for checking of the unique login of the user.
	 * 
	 * @param login - {@link String}
	 * @return count of login
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int uniqueLogin(String login) throws DAOException;
	
	
	/**
	 * Method for checking of the unique email of the user.
	 * 
	 * @param email - {@link String}
	 * @return count of login
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int uniqueEmail(String email) throws DAOException;
	
	
	/**
	 * Method for calculation of active tariffs at the user.
	 * 
	 * @param userID - {@link User#id}
	 * @return count of active tariffs
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int countActiveTariffs(int userID) throws DAOException;
	
	
	/**
	 * Method for searching with parameters of the users.
	 * 
	 * @return List of the users - {@link List}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public List<User> searchWithParameters() throws DAOException;
	
	
	/**
	 * Method for searching of the user by id.
	 * 
	 * @param id - {@link User#id}
	 * @return User - {@link User}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public User searchById(int id) throws DAOException;
	
	
	/**
	 * Method for searching of the name of the active tariff at the user.
	 * 
	 * @param userID - {@link User#id}
	 * @return name of active tariff - {@link String}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public String searchActiveTariff(int userID) throws DAOException;
	
	
	/**
	 * Method for deleting of the user by id.
	 * 
	 * @param id - {@link User#id}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void delete(int id) throws DAOException;
	
	
	/**
	 * Method for searching of users with the negative balance.
	 * 
	 * @return List of the users - {@link List}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public List<User> negativeBalance() throws DAOException;
	
	
	/**
	 * Method for calculation of active bans at the user.
	 * 
	 * @param userID - {@link User#id}
	 * @return count of active bans
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int countActiveBan(int userID) throws DAOException;
	
	
	/**
	 * Method for searching of users with unlim tariffs which more than 30 days.
	 * 
	 * @return List of the users - {@link List}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public List<User> usersWithUnlimMore30Days() throws DAOException;
	
	
	/**
	 * Method for searching of the contract's number of the user 
	 * 
	 * @param userID - {@link User#id}
	 * @return number of contract
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int searchContractId(int userID) throws DAOException;
	
}
