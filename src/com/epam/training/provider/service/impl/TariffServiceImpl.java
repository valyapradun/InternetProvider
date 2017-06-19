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
	
	
}
