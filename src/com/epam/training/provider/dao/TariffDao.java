package com.epam.training.provider.dao;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.dao.exception.DAOException;
/**
 * Interface of data access object for the operations with a tariff.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface TariffDAO {
	
	/**
	 * Method for searching with parameters of the tariffs.
	 * 
	 * @param parameters - {@link Map}
	 * @return List of the tariffs - {@link List}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public List<Tariff> searchWithParameters(Map<String, String> parameters) throws DAOException;
	
	
	/**
	 * Method for searching of the tariff by id.
	 * 
	 * @param id - {@link Tariff#id}
	 * @return tariff - {@link Tariff}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public Tariff searchById(int id) throws DAOException;
	
	
	/**
	 * Method for editing of the tariff.
	 * 
	 * @param tariff - {@link Tariff}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void edit(Tariff tariff) throws DAOException;
	
	
	/**
	 * Method for adding of new tariff.
	 * 
	 * @param tariff - {@link Tariff}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void addNew(Tariff tariff) throws DAOException;
	
	
	/**
	 * Method for deleting of the tariff by id.
	 * 
	 * @param id - {@link Tariff#id}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void delete(int id) throws DAOException;
	
	
	/**
	 * Method for updating of the date of end (change on current date)
	 * 
	 * @param idContract - {@link User#numberContract}
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public void endTariff(int idContract) throws DAOException;
	
	
	/**
	 * Method for checking of the unique tariff
	 * 
	 * @param tariff - {@link Tariff}
	 * @return count of the tariffs
	 * @throws DAOException Exception from the SQLException or ConnectionPoolException  
	 */
	public int uniqueTariff(Tariff tariff) throws DAOException;
	
}
