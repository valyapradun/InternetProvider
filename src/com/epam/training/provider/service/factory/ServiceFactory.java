package com.epam.training.provider.service.factory;

import com.epam.training.provider.service.TariffService;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.impl.TariffServiceImpl;
import com.epam.training.provider.service.impl.PaymentServiceImpl;
import com.epam.training.provider.service.impl.UserServiceImpl;
/**
 * Singletone-class of ServiceFactory.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public final class ServiceFactory {
	/** Single instance of the class ServiceFactory */
	private static final ServiceFactory instance = new ServiceFactory();

	/** Instance of the class-implementation UserService */
	private final UserService userService = new UserServiceImpl();
	
	/** Instance of the class-implementation TariffService */
	private final TariffService tariffService = new TariffServiceImpl();
	
	/** Instance of the class-implementation PaymentService */
	private final PaymentService paymentService = new PaymentServiceImpl();

	
	private ServiceFactory() {
	}

	
	/** Method for returning a ServiceFactory instance 
	 * 
	 * @return instance - {@link ServiceFactory}
	 */
	public static ServiceFactory getInstance() {
		return instance;
	}

	/** Method for returning a UserService instance */
	public UserService getUserService() {
		return userService;
	}

	/** Method for returning a TariffService instance */
	public TariffService getTariffService() {
		return tariffService;
	}

	/** Method for returning a PaymentService instance */
	public PaymentService getPaymentService() {
		return paymentService;
	}
}
