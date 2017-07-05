package com.epam.training.provider.service.impl;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.exception.ServiceException;

public class TariffServiceImpl implements TariffService {

	@Override
	public List<Tariff> listTariffsWithParameters(HashMap<String, String> parameters) throws ServiceException {
		if (parameters.isEmpty()) {
			throw new ServiceException("Parameters in listTariffsWithParameters() weren't transferred!");
		}

		List<Tariff> tariffs = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			tariffs = dao.searchWithParameters(parameters);
		} catch (DAOException e) {
			throw new ServiceException("Search of tariffs with parameters wasn't executed!", e);
		}

		return tariffs;
	}

	@Override
	public Tariff tariffById(int id) throws ServiceException {
		if (id <= 0) {
			throw new ServiceException("ID of tariff is less or is equal to 0!");
		}

		Tariff tariff = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			tariff = dao.searchById(id);
		} catch (DAOException e) {
			throw new ServiceException("Search of tariff by id wasn't executed!", e);
		}

		return tariff;
	}

	@Override
	public void editTariff(Tariff tariff) throws ServiceException {
		if (tariff == null) {
			throw new ServiceException("The tariff for editing is equal to null!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			dao.edit(tariff);
		} catch (DAOException e) {
			throw new ServiceException("Editing a tariff wasn't executed!", e);
		}
	}

	@Override
	public void addTariff(Tariff tariff) throws ServiceException {
		if (tariff == null) {
			throw new ServiceException("The tariff for adding is equal to null!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();
			dao.addNew(tariff);
		} catch (DAOException e) {
			throw new ServiceException("Adding a tariff wasn't executed!", e);
		}
	}

	@Override
	public void deleteTariff(int id) throws ServiceException {
		if (id <= 0) {
			throw new ServiceException("ID of tariff is less or is equal to 0!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			TariffDAO dao = daoObjectFactory.getTariffDAO();

			dao.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Deleting a tariff wasn't executed!", e);
		}
	}
}
