package com.epam.training.provider.dao;

import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.dao.exception.DAOException;

public interface TariffDAO {
	
	public List<Tariff> searchWithParameters(Map<String, String> parameters) throws DAOException;
	public Tariff searchById(int id) throws DAOException;
	public void edit(Tariff tariff) throws DAOException;
	public void addNew(Tariff tariff) throws DAOException;
	public void delete(int id) throws DAOException;
	public void endTariff(int idContract) throws DAOException;
	public int uniqueTariff(Tariff tariff) throws DAOException;
	
}
