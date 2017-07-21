package com.epam.training.provider.dao;

import com.epam.training.provider.bean.Ban;
import com.epam.training.provider.dao.exception.DAOException;

public interface BanDAO {
	public void addNew(Ban newBan) throws DAOException;
}
