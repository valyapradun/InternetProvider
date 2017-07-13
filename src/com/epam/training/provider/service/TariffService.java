package com.epam.training.provider.service;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.service.exception.ServiceException;

public interface TariffService {
	
	public List<Tariff> listTariffsWithParameters(Map <String, String> parameters) throws ServiceException;
	public Tariff tariffById(int id) throws ServiceException;
	public void editTariff(Tariff tariff) throws ServiceException;
	public void addTariff(Tariff tariff) throws ServiceException;
	public void deleteTariff(int id) throws ServiceException;
	
}
