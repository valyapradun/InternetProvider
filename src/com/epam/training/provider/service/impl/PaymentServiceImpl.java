package com.epam.training.provider.service.impl;

import static com.epam.training.provider.util.Permanent.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
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

/**
 * Class of business-logic for the payments operations.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */

public class PaymentServiceImpl implements PaymentService {
	
	/**
	 * Method for searching of payments by criteria.
	 * 
	 * @param Map<String, String> 
	 * @return List of payments
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	@Override
	public List<Payment> listPaymentsWithCriteria(Map<String, String> criteria) throws ServiceException, ValidateException {
		if (criteria.isEmpty()) {
			throw new ValidateException("Criteria in listPaymentsWithCriteria weren't transferred!");
		}

		List<Payment> payments = null;

		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		PaymentDAO dao = daoObjectFactory.getPaymentDAO();

		try {
			payments = dao.searchWithParameters(criteria);
		} catch (DAOException e) {
			throw new ServiceException("Search of payments with criteria wasn't executed!" + e.getMessage(), e);
		}

		return payments;
	}

	/**
	 * Method for adding of payments.
	 * 
	 * @param payment {@link Payment} 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	@Override
	public void addPayment(Payment payment) throws ServiceException, ValidateException {
		String errors = validatePayment(payment);
		if (errors.isEmpty()) {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			PaymentDAO dao = daoObjectFactory.getPaymentDAO();
			try {
				dao.addNew(payment);
			} catch (DAOException e) {
				throw new ServiceException("Add of transaction wasn't executed!" + e.getMessage(), e);
			}
		} else {
			throw new ValidateException(errors);
		}
	}

	
	/**
	 * Method for buying of tariff.
	 * 
	 * <ol>
	 *   <li>To check an active tariff at the user</li>
	 *   <li>To find price of tariff</li>
	 *   <li>To check a balance at the user</li>
	 *   <li>To buy this tariff:
	 *    <ul>
	 *       <li>to add new row in the table 'user_to_tariff'</li>
	 *       <li>to add new payment (type - WITHDRAW)</li>
	 *       <li>to update the balance of user</li>
	 *     </ul>
	 *   </li>
	 * </ol>
	 * 
	 * @param userID {@link User#id}
	 * @param tariffID {@link Tariff#id}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 * @see PaymentServiceImpl#checkActiveTariffs(int userID)
	 * @see TariffDAOImpl#searchById(int id)
	 * @see PaymentServiceImpl#checkBalanceUser(int userID, double priceTariff)
	 * @see PaymentDAOImpl#buyNewTariff(Payment payment, int idTariff) 
	 *           
	 */
	@Override
	public void buyTariff(int userID, int tariffID) throws ServiceException, ValidateException {
		if ((userID <= 0) || (tariffID <= 0)) {
			throw new ValidateException("ID of tariff (or ID of user) is less or is equal to 0!");
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
			throw new ServiceException("Сan't buy the tariff! " + e.getMessage(), e);
		}

	}

	
	/**
	 * Method for checking of active tariff.
	 * 
	 * Calculation of the count of active tariffs at the user (date of tariff's end is NULL). 
	 * To throw ValidateException if count > 0.
	 * 
	 * @param userID {@link User#id}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 * @see UserDAOImpl#countActiveTariffs(int userID)
	 *           
	 */
	public void checkActiveTariffs(int userID) throws ValidateException, ServiceException {
		if (userID <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}

		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		int countActiveTariffs;
		try {
			countActiveTariffs = daoUser.countActiveTariffs(userID);
			if (countActiveTariffs > 0) {
				throw new ValidateException("You already have an active tariff! Finish its action, having sent the request in the section Contacts.");
			}
		} catch (DAOException e) {
			throw new ServiceException("Сan't count active tariffs! " + e.getMessage(), e);
		}
	}

	
	/**
	 * Method for checking of balance at the user.
	 * 
	 * To throw ValidateException if price of tariff > balance of user.
	 * 
	 * @param userID {@link User#id}
	 * @param priceTariff {@link Tariff#price}
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 * @see UserDAOImpl#searchById(int id)
	 *           
	 */
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
			throw new ServiceException("Сan't search user by ID! " + e.getMessage(), e);
		}
	}

	
	/**
	 * Method for validating of payment.
	 * 
	 * <ol>
	 *   <li>Check on payment == null</li>
	 *   <li>Check on the required field (amount)</li>
	 * </ol>
	 * 
	 * @param payment {@link Payment}
	 * @return String of errors
	 * @throws ValidateException Validations errors
	 * @see Validate#checkRequiredDoubleField(String nameField, double valuefield)
	 *           
	 */
	public String validatePayment(Payment payment) throws ValidateException {
		if (payment == null) {
			throw new ValidateException("Payment is equal to null!");
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(Validate.checkRequiredDoubleField(PAYMENT_AMOUNT, payment.getAmount()));
		return buffer.toString();

	}

	
	/**
	 * Method for prolonging of unlim-tariffs.
	 * 
	 * <ol>
	 *   <li>To choose all users who have UNLIM tariff with:
	 *     <ul>
	 *       <li>date of tariff's end is NULL</li>
	 *       <li>date of tariff's beginning more current date than 30 days</li>
	 *     </ul>
	 *   </li>
	 *   <li>For these users to change the date of tariff's on current date.</li>
	 *   <li>For these users to buy those tariffs again:
	 *    <ul>
	 *       <li>to add new row in the table 'user_to_tariff'</li>
	 *       <li>to add new payment (type - WITHDRAW)</li>
	 *       <li>to update the balance of user</li>
	 *     </ul>
	 *   </li>
	 * </ol>
	 * 
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException 
	 * @see UserDAOImpl#usersWithUnlimMore30Days()
	 * @see TariffDAOImpl#endTariff(int idContract)    
	 * @see PaymentDAOImpl#buyNewTariff(Payment payment, int idTariff)          
	 */

	@Override
	public void prolongUnlimTariffs() throws ServiceException {
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();
		TariffDAO daoTariff = daoObjectFactory.getTariffDAO();
		PaymentDAO daoPayment = daoObjectFactory.getPaymentDAO();

		List<User> users = new ArrayList<User>();
		try {
			users = daoUser.usersWithUnlimMore30Days();

			if (users != null) {

				Iterator<User> iterator = users.iterator();
				while (iterator.hasNext()) {
					User user = iterator.next();

					if (daoUser.countActiveBan(user.getId()) == 0) {	
					
						daoTariff.endTariff(user.getNumberContract());

						PaymentType type = PaymentType.WITHDRAW;
						Calendar currentDate = Calendar.getInstance();
						Date date = currentDate.getTime();
						Payment payment = new Payment(type, (user.getTariff()).getPrice(), date, user.getId());
						daoPayment.buyNewTariff(payment, (user.getTariff()).getId());
					}
				}
			}

		} catch (DAOException e) {
			throw new ServiceException("Сan't search users! " + e.getMessage(), e);
		}
	}

}
