package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

public interface TariffService {
	
	public List<Tariff> listTariffsWithCriteria(Map <String, String> criteria) throws ServiceException, ValidateException;
	public Tariff tariffById(int id) throws ServiceException, ValidateException;
	public void editTariff(Tariff tariff) throws ServiceException, ValidateException;
	public void addTariff(Tariff tariff) throws ServiceException, ValidateException;
	public void deleteTariff(int id) throws ServiceException, ValidateException;
	public String checkUniqueTariff(String nameTariff) throws ServiceException, ValidateException;
	
}
