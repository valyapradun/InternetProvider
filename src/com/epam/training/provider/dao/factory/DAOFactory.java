package com.epam.training.provider.dao.factory;

import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.BanDAO;
import com.epam.training.provider.dao.PaymentDAO;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.impl.TariffDAOImpl;
import com.epam.training.provider.dao.impl.BanDAOImpl;
import com.epam.training.provider.dao.impl.PaymentDAOImpl;
import com.epam.training.provider.dao.impl.UserDAOImpl;

/**
 * Singletone-class of DAOFactory.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public final class DAOFactory {
	/** Single instance of the class DAOFactory */
	private static final DAOFactory instance = new DAOFactory();

	/** Instance of the class-implementation UserDAO */
	private final UserDAO userImpl = new UserDAOImpl();
	
	/** Instance of the class-implementation TariffDAO */
	private final TariffDAO tariffImpl = new TariffDAOImpl();
	
	/** Instance of the class-implementation PaymentDAO */
	private final PaymentDAO paymentImpl = new PaymentDAOImpl();
	
	/** Instance of the class-implementation BanDAO */
	private final BanDAO banImpl = new BanDAOImpl();

	
	private DAOFactory() {
	}

	/** Method for returning a DAOFactory instance 
	 * 
	 * @return instance - {@link instance}
	 */
	public static DAOFactory getInstance() {
		return instance;
	}

	
	/** Method for returning a UserDAO instance */
	public UserDAO getUserDAO() {
		return userImpl;
	}

	/** Method for returning a TariffDAO instance */
	public TariffDAO getTariffDAO() {
		return tariffImpl;
	}
	
	/** Method for returning a PaymentDAO instance */
	public PaymentDAO getPaymentDAO() {
		return paymentImpl;
	}
	
	/** Method for returning a BanDAO instance */
	public BanDAO getBanDAO() {
		return banImpl;
	}

}
