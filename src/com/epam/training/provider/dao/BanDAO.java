package com.epam.training.provider.dao;

import com.epam.training.provider.bean.Ban;
import com.epam.training.provider.dao.exception.DAOException;

/**
 * Interface of data access object for the operations with a ban.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface BanDAO {

	/**
	 * Method for adding the ban to user.
	 * 
	 * @param newBan - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void addNew(Ban newBan) throws DAOException;
	
	
	/**
	 * Method for editing date of end of ban.
	 * 
	 * @param ban - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void edit(Ban ban) throws DAOException; 
	
	
	/**
	 * Method for searching active ban of user.
	 * 
	 * @param userID - {@link User#id}
	 * @return Ban - {@link Ban}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public Ban activeBan(int userID) throws DAOException;
}
