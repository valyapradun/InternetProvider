package com.epam.training.provider.service.impl;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import static com.epam.training.provider.util.Permanent.*;

/**
 * Class-implementation of business-logic for the operations of tariff.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class TariffServiceImpl implements TariffService {
	
	/**
	 * Method for adding of tariff.
	 * 
	 * @param tariff {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors   
	 */
	@Override
	public void addTariff(Tariff tariff) throws ServiceException, ValidateException {
		String errors = validateTariff(tariff);
		if (errors.isEmpty()) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				TariffDAO dao = daoObjectFactory.getTariffDAO();
				dao.addNew(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Adding a tariff wasn't executed!" + e.getMessage(), e);
			}
		} else {
			throw new ValidateException(errors);
		}
	}
	
	/**
	 * Method for editing of tariff.
	 * 
	 * @param tariff {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors      
	 */
	@Override
	public void editTariff(Tariff tariff) throws ServiceException, ValidateException {
		String errors = validateTariff(tariff);
		if (errors.isEmpty()) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				TariffDAO dao = daoObjectFactory.getTariffDAO();
				dao.edit(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Editing a tariff wasn't executed!" + e.getMessage(), e);
			}
		} else {
			throw new ValidateException(errors);
		}
	}
	
	
	/**
	 * Method for searching of tariff by id.
	 * 
	 * @param id - {@link Tariff#id} 
	 * @return tariff - {@link Tariff} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors         
	 */
	@Override
	public Tariff tariffById(int id) throws ServiceException, ValidateException {
		if (id <= 0) {
			throw new ValidateException("ID of tariff is less or is equal to 0!");
		}

		Tariff tariff = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			tariff = dao.searchById(id);
			
			if (tariff == null) {
			  throw new ValidateException("Such tariff (id = " + id + ") doesn't exist!");
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Search of tariff by id wasn't executed!" + e.getMessage(), e);
		}

		return tariff;
	}

	
	/**
	 * Method for deleting of tariff by id.
	 * 
	 * @param id - {@link Tariff#id} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors  
	 */
	@Override
	public void deleteTariff(int id) throws ServiceException, ValidateException {
		if (id <= 0) {
			throw new ValidateException("ID of tariff is less or is equal to 0!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			dao.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Deleting a tariff wasn't executed! " + e.getMessage(), e);
		}
	}

	
	/**
	 * Method for searching of tariff by criteria.
	 * 
	 * @param criteria - {@link Map}
	 * @return list of tariffs - {@link List}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors       
	 */
	@Override
	public List<Tariff> listTariffsWithCriteria(Map<String, String> criteria) throws ServiceException, ValidateException {
		if (criteria.isEmpty()) {
			throw new ValidateException("Criteria in listTariffsWithParameters() weren't transferred!");
		}

		List<Tariff> tariffs = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			tariffs = dao.searchWithParameters(criteria);
		} catch (DAOException e) {
			throw new ServiceException("Search of tariffs with parameters wasn't executed!" + e.getMessage(), e);
		}

		return tariffs;
	}
	
	
	/**
	 * Method for checking of uniqueness of a name of tariff.
	 * Calculation of the count of tariffs with current name. 
	 * Throw ValidateException if count > 0.
	 * 
	 * @param tariff - {@link Tariff}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors        
	 */
	@Override
	public String checkUniqueTariff(Tariff tariff) throws ServiceException, ValidateException {
		if (tariff == null) {
			throw new ValidateException("The tariff is equal to null! ");
		}
		
		String result = "";
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();

			int count = dao.uniqueTariff(tariff);
			if (count > 0) {
				result = "- Such tariff's name already exists! ";
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of tariff's name wasn't executed! " + e.getMessage(), e);
		}
		
		return result;
	}
	
	
	
	/**
	 * Method for validating of tariff.
	 * 
	 * <ol>
	 *   <li>Check on tariff == null</li>
	 *   <li>Check on the required field (name of tariff)</li>
	 *   <li>Check on the required field (price of tariff)</li>
	 *   <li>Check on the required field (type of tariff)</li>
	 *   <li>Check on admissible symbols (name of tariff)</li>
	 *   <li>Check of uniqueness of a tariff</li>
	 * </ol>
	 * @param tariff {@link Tariff}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	private String validateTariff(Tariff tariff) throws ValidateException, ServiceException {
		if (tariff == null) {
			throw new ValidateException("The tariff is equal to null! ");
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Validate.checkRequiredStringField(TARIFF_NAME, tariff.getName()));
		buffer.append(Validate.checkRequiredDoubleField(TARIFF_PRICE, tariff.getPrice()));
		buffer.append(Validate.checkRequiredStringField(TARIFF_TYPE, tariff.getType().toString()));
		buffer.append(Validate.checkTariffName(tariff.getName()));
		buffer.append(checkUniqueTariff(tariff));

		return buffer.toString();

	}
		
}
