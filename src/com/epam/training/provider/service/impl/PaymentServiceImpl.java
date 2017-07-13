package com.epam.training.provider.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.epam.training.provider.bean.Payment;
import com.epam.training.provider.bean.PaymentType;
import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.PaymentDAO;
import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
import com.epam.training.provider.service.PaymentService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;

public class PaymentServiceImpl implements PaymentService {

	@Override
	public List<Payment> listPaymentsWithParameters(Map<String, String> parameters) throws ServiceException {
		if (parameters.isEmpty()) {
			throw new ServiceException("Parameters in listTransactionWithParameters weren't transferred!");
		}
		List<Payment> payments = null;

		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		PaymentDAO dao = daoObjectFactory.getPaymentDAO();

		try {
			payments = dao.searchWithParameters(parameters);
		} catch (DAOException e) {
			throw new ServiceException("Search of transactions with parameters wasn't executed!", e);
		}

		return payments;
	}

	@Override
	public void addPayment(Payment payment) throws ServiceException {
		if (payment == null) {
			throw new ServiceException("Transaction in addTransaction wasn't transferred!");
		}

		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		PaymentDAO dao = daoObjectFactory.getPaymentDAO();
		try {
			dao.addNew(payment);
		} catch (DAOException e) {
			throw new ServiceException("Add of transaction wasn't executed!", e);
		}

	}

	@Override
	public void buyTariff(int userID, int tariffID) throws ServiceException, ValidateException {
		if ((userID <= 0) || (tariffID <= 0)) {
			throw new ServiceException("ID of tariff (or ID of user) is less or is equal to 0!");
		}

		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		TariffDAO daoTariff = daoObjectFactory.getTariffDAO();
		PaymentDAO daoPayment = daoObjectFactory.getPaymentDAO();

		checkActiveTariffs(userID);
		
		try {
			Tariff tariff = daoTariff.searchById(tariffID);
			double priceTariff = tariff.getPrice();
			checkBalanceUser(userID, priceTariff);
			
			PaymentType type = PaymentType.WITHDRAW;
			Calendar currentDate = Calendar.getInstance();
			Date date = currentDate.getTime();
			Payment payment = new Payment(type, priceTariff, date, userID);

			daoPayment.buyNewTariff(payment, tariffID);
			
		} catch (DAOException e) {
			throw new ServiceException("Сan't buy the tariff! ", e);
		}

	}

	public void checkActiveTariffs(int userID) throws ValidateException, ServiceException {
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		int countActiveTariffs;
		try {
			countActiveTariffs = daoUser.countActiveTariffs(userID);
			if (countActiveTariffs > 0) {
				throw new ValidateException("You already have an active tariff! Finish its action, having sent the request in the section Contacts.");
			}
		} catch (DAOException e) {
			throw new ServiceException("Сan't count active tariffs! ", e);
		}
	}

	public void checkBalanceUser(int userID, double priceTariff) throws ValidateException, ServiceException {
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		try {
			User user = daoUser.searchById(userID);
			double balanceUser = user.getBalance();

			if (balanceUser < priceTariff) {
				throw new ValidateException("You don't have enough money on the account! ");
			}

		} catch (DAOException e) {
			throw new ServiceException("Сan't search user by ID! ", e);
		}
	}

}
