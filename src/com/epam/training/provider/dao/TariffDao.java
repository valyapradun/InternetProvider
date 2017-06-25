package com.epam.training.provider.dao;

import java.util.HashMap;
import java.util.List;

import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.dao.exception.DAOException;

public interface TariffDAO {
	
	public List<Tariff> searchWithParameters(HashMap<String, String> parameters) throws DAOException;
	public Tariff searchById(int id) throws DAOException;
	
}
