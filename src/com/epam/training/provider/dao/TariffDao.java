package com.epam.training.provider.dao;

import java.util.List;

import com.epam.training.provider.bean.Tariff;

public interface TariffDao {
	
	// create
	public List<Tariff> viewAll() throws TariffDaoException;
	// update
	// delete
}
