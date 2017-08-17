package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
/**
 * Interface of business-logic for the operations of tariff.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public interface TariffService {
	/**
	 * Method for searching of tariff by criteria.
	 * 
	 * @param criteria - {@link Map}
	 * @return list of tariffs - {@link List}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors       
	 */
	public List<Tariff> listTariffsWithCriteria(Map <String, String> criteria) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for searching of tariff by id.
	 * 
	 * @param id - {@link Tariff#id} 
	 * @return tariff - {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */
	public Tariff tariffById(int id) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for editing of tariff.
	 * 
	 * @param tariff {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors      
	 */
	public void editTariff(Tariff tariff) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for adding of tariff.
	 * 
	 * @param tariff {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors   
	 */
	public void addTariff(Tariff tariff) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for deleting of tariff by id.
	 * 
	 * @param id - {@link Tariff#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors  
	 */
	public void deleteTariff(int id) throws ServiceException, ValidateException;
	
	
	/**
	 * Method for checking of uniqueness of a name of tariff.
	 * Calculation of the count of tariffs with current name. 
	 * Throw ValidateException if count > 0.
	 * 
	 * @param nameTariff - {@link Tariff#name}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors        
	 */
	public String checkUniqueTariff(String nameTariff) throws ServiceException, ValidateException;
	
}
