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

public class TariffServiceImpl implements TariffService {

	@Override
	public void addTariff(Tariff tariff) throws ServiceException, ValidateException {
		String errors = validateTariff(tariff);
		if (errors.isEmpty()) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				TariffDAO dao = daoObjectFactory.getTariffDAO();
				dao.addNew(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Adding a tariff wasn't executed!", e);
			}
		} else {
			throw new ValidateException(errors);
		}
	}
	
	
	@Override
	public void editTariff(Tariff tariff) throws ServiceException, ValidateException {
		String errors = validateTariff(tariff);
		if (errors.isEmpty()) {
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				TariffDAO dao = daoObjectFactory.getTariffDAO();
				dao.edit(tariff);
			} catch (DAOException e) {
				throw new ServiceException("Editing a tariff wasn't executed!", e);
			}
		} else {
			throw new ValidateException(errors);
		}
	}
	
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
			throw new ServiceException("Search of tariff by id wasn't executed!", e);
		}

		return tariff;
	}

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
			throw new ServiceException("Deleting a tariff wasn't executed!", e);
		}
	}

	
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
			throw new ServiceException("Search of tariffs with parameters wasn't executed!", e);
		}

		return tariffs;
	}
	
	@Override
	public String checkUniqueTariff(String nameTariff) throws ServiceException, ValidateException {
		if (nameTariff == null) {
			throw new ValidateException("The tariff's name is equal to null!");
		}
		
		String result = "";
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();

			int count = dao.uniqueTariff(nameTariff);
			if (count > 0) {
				result = "- Such tariff's name already exists! ";
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of tariff's name wasn't executed! ", e);
		}
		
		return result;
	}
		
	
	public String validateTariff(Tariff tariff) throws ValidateException, ServiceException {
		if (tariff == null) {
			throw new ValidateException("The tariff is equal to null! ");
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Validate.checkRequiredStringField(TARIFF_NAME, tariff.getName()));
		buffer.append(Validate.checkRequiredDoubleField(TARIFF_PRICE, tariff.getPrice()));
		buffer.append(Validate.checkRequiredStringField(TARIFF_TYPE, tariff.getType().toString()));
		buffer.append(Validate.checkTariffName(tariff.getName()));
		buffer.append(checkUniqueTariff(tariff.getName()));
		
		return buffer.toString();

	}
		
}
