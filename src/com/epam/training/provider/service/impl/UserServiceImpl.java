package com.epam.training.provider.service.impl;

import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import com.epam.training.provider.bean.Ban;
import com.epam.training.provider.bean.Tariff;
import com.epam.training.provider.bean.User;
import com.epam.training.provider.dao.BanDAO;
import com.epam.training.provider.dao.TariffDAO;
import com.epam.training.provider.dao.UserDAO;
import com.epam.training.provider.dao.exception.DAOException;
import com.epam.training.provider.dao.factory.DAOFactory;
import com.epam.training.provider.service.UserService;
import com.epam.training.provider.service.exception.ServiceException;
import com.epam.training.provider.service.exception.ValidateException;
import static com.epam.training.provider.util.Permanent.*;

/**
 * Class-implementation of business-logic for the operations of user.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class UserServiceImpl implements UserService {
	private static final String NO_PAYMENT = "no payment";
	
	@Override
	public User authorize(String login, String password) throws ServiceException, ValidateException {		
		String errors = validateLoginPassword(login, password);
		
		if (errors.isEmpty()) {
			
			User user = null;
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				UserDAO dao = daoObjectFactory.getUserDAO();
				user = dao.signIn(login, password);
			} catch (DAOException e) {
				throw new ServiceException("Authorization wasn't executed! " + e.getMessage(), e);
			}
			return user;
			
		} else {
			throw new ValidateException(errors);
		}
	}
	
	
	@Override
	public void registration(User newUser) throws ServiceException, ValidateException {
		String errors = validateUser(newUser);
		
		if (errors.isEmpty()) {
			// Add new row in table 'user'.
			try {
				DAOFactory daoObjectFactory = DAOFactory.getInstance();
				UserDAO dao = daoObjectFactory.getUserDAO();
				dao.registration(newUser);
			} catch (DAOException e) {
				throw new ServiceException("Registration wasn't executed! " + e.getMessage(), e);
			}
		} else {
			throw new ValidateException(errors);
		}

	}
	
	
	@Override
	public User userById(int id) throws ServiceException, ValidateException {
		if (id <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}

		User user = null;
		Tariff tariff = new Tariff();
		
		try {

			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			
			user = dao.searchById(id);
			String tariffName = dao.searchActiveTariff(id);
			tariff.setName(tariffName);
			user.setTariff(tariff);
			user.setActiveBan(checkActiveBan(id));
			
		} catch (DAOException e) {
			throw new ServiceException("Search of users wasn't executed! " + e.getMessage(), e);
		}
		return user;
	}
	
	
	@Override
	public String checkUniqueLogin(String login) throws ServiceException, ValidateException {
		if (login == null) {
			throw new ValidateException("The login is equal to null!");
		}
		
		String result = "";
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			int count = dao.uniqueLogin(login);
			if (count > 0) {
				result = "- Such login already exists! ";
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of login wasn't executed! " + e.getMessage(), e);
		}
		
		return result;
	}

	
	@Override
	public String checkUniqueEmail(String email) throws ServiceException, ValidateException {
		if (email == null) {
			throw new ValidateException("The email is equal to null!");
		}
		
		String result = "";
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			int count = dao.uniqueEmail(email);
			if (count > 0) {
				result = "- Such email already exists! ";
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Uniqueness of email wasn't executed! " + e.getMessage(), e);
		}
		
		return result;
	}


	
	@Override
	public List<User> listUsersWithParameters() throws ServiceException {
		List<User> users = null;
		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();
			users = dao.searchWithParameters();
		} catch (DAOException e) {
			throw new ServiceException("Search of users wasn't executed! " + e.getMessage(), e);
		}
		return users;
	}

	

	@Override
	public void deleteUser(int id) throws ServiceException, ValidateException {
		if (id <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}

		try {
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO dao = daoObjectFactory.getUserDAO();

			dao.delete(id);
		} catch (DAOException e) {
			throw new ServiceException("Deleting the user wasn't executed! " + e, e);
		}
	}
	
	
	/**
	 * Method for putting of bans.
	 * <ol>
	 *   <li>Search users with a negative balance</li>
	 *   <li>For these users add new row in table 'ban' with a reason 'no payment'</li>
	 * </ol>         
	 */
	@Override
	public void putBan(int adminId) throws ServiceException, ValidateException {
		if (adminId <= 0) {
			throw new ValidateException("ID of administrator is less or is equal to 0!");
		}
		
		try {
			
			DAOFactory daoObjectFactory = DAOFactory.getInstance();
			UserDAO daoUser = daoObjectFactory.getUserDAO();

			List<User> users = daoUser.negativeBalance();
			
			if (users != null) {
				
				Iterator<User> iterator = users.iterator();
		        while(iterator.hasNext()){
		        	User user = iterator.next();
		        		if (!checkActiveBan(user.getId())) {
		        			Ban ban = new Ban();
							ban.setUserId(user.getId());
				            ban.setAdminId(adminId);
				            ban.setStartDate(Calendar.getInstance().getTime());
				            ban.setReason(NO_PAYMENT);
				            
							BanDAO daoBan = daoObjectFactory.getBanDAO();
							daoBan.addNew(ban);
		        		}
		        }
			}
		} catch (DAOException e) {
			throw new ServiceException("It wasn't succeeded to put the ban!" + e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * Method for validating login and password.
	 * 
	 * <ol>
	 *   <li>Check on the required field (login of user)</li>
	 *   <li>Check on the required field (password of user)</li>
	 *   <li>Check on admissible symbols (login of user)</li>
	 *   <li>Check on admissible symbols (password of user)</li>
	 * </ol>
	 * @param login {@link User#login}
	 * @param password {@link User#password}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	private String validateLoginPassword(String login, String password) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Validate.checkRequiredStringField(USER_LOGIN, login));
		buffer.append(Validate.checkRequiredStringField(USER_PASSWORD, password));
		buffer.append(Validate.checkLatinSymbol(login));
		buffer.append(Validate.checkDifficultPassword(password));

		return buffer.toString();
	}

	
	/**
	 * Method for validating of user.
	 * 
	 * <ol>
	 *   <li>Check on user == null</li>
	 *   <li>Check on the required field (login of user)</li>
	 *   <li>Check on the required field (password of user)</li>
	 *   <li>Check on the required field (name of user)</li>
	 *   <li>Check on the required field (email of user)</li>
	 *   <li>Check of uniqueness of a login of user</li>
	 *   <li>Check of uniqueness of a email of user</li>
	 *   <li>Check on admissible symbols (login of user)</li>
	 *   <li>Check on admissible symbols (password of user)</li>
	 *   <li>Check on admissible symbols (email of user)</li>
	 * </ol>
	 * @param newUser {@link User}
	 * @return String of errors
	 * @throws ServiceException Exception from the DAO-level
	 * @throws ValidateException Validations errors
	 *           
	 */
	private String validateUser(User newUser) throws ServiceException, ValidateException {
		if (newUser == null) {
			throw new ValidateException("The user is equal to null! ");
		}
		
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(Validate.checkRequiredStringField(USER_LOGIN, newUser.getLogin()));
		buffer.append(Validate.checkRequiredStringField(USER_PASSWORD, newUser.getPassword()));
		buffer.append(Validate.checkRequiredStringField(USER_NAME, newUser.getName()));
		buffer.append(Validate.checkRequiredStringField(USER_EMAIL, newUser.getEmail()));
		buffer.append(checkUniqueLogin(newUser.getLogin()));
		buffer.append(checkUniqueEmail(newUser.getEmail()));
		buffer.append(Validate.checkLatinSymbol(newUser.getLogin()));
		buffer.append(Validate.checkDifficultPassword(newUser.getPassword()));
		buffer.append(Validate.checkValidEmail(newUser.getEmail()));

		return buffer.toString();

	}

	@Override
	public boolean checkActiveBan(int userID) throws ValidateException, ServiceException {
		if (userID <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}
		
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		int countActiveBan;
		try {
			countActiveBan = daoUser.countActiveBan(userID);
			if (countActiveBan > 0) {
				return true;
			}

		} catch (DAOException e) {
			throw new ServiceException("Сan't count active tariffs! " + e.getMessage(), e);
		}
		
		return false;
	}

	
	/**
	 * Method for removing of ban.
	 * 
	 * <ol>
	 *   <li>Check of the active ban at the current user</li>
	 *   <li>Check of the negative balance at the current user</li>
	 *   <li>Change the date of end of ban's on current date</li>
	 * </ol>       
	 */
	@Override
	public void removeBan(int adminId, int userId) throws ServiceException, ValidateException {
		if ((adminId <= 0) || (userId <= 0)) {
			throw new ValidateException("ID of administrator is less or is equal to 0!");
		}
		
		
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		try {
			User user = daoUser.searchById(userId);
			
			if (user != null) {
				if (!checkNegativeBalance(userId) && checkActiveBan(userId)){
					
					BanDAO daoBan = daoObjectFactory.getBanDAO();
					Ban ban = daoBan.activeBan(userId);
					ban.setEndDate(Calendar.getInstance().getTime());
					daoBan.edit(ban);
				} else {
					throw new ValidateException("User hasn't active ban or enough money!");
				}	
			}
		} catch (DAOException e) {
			throw new ServiceException("Сan't remove the ban! " + e.getMessage(), e);
		}
	}
	
	

	@Override
	public boolean checkNegativeBalance(int userID) throws ValidateException, ServiceException {
		if (userID <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}
		
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();
		User user;

		try {
			user = daoUser.searchById(userID);
			if (user.getBalance() < 0) {
				return true;
			}

		} catch (DAOException e) {
			throw new ServiceException("Сan't count search of the user! ", e);
		}
		
		return false;
	}

	
	
	@Override
	public void endTariff(int userId) throws ServiceException, ValidateException {
		if (userId <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}
		
		// Check of an active tariff and an active ban of the user
		if (!checkActiveTariff(userId) || checkActiveBan(userId)) {
			throw new ValidateException("User hasn't active tariff or he has active ban!");
		}
		
		
		// Get ID contract of the user and end the tariff
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();
		TariffDAO daoTariff = daoObjectFactory.getTariffDAO();
		
		try {
			int contract = daoUser.searchContractId(userId);
			if (contract != 0) {
				daoTariff.endTariff(contract);
			}
			
		} catch (DAOException e) {
			throw new ServiceException("Can't end the tariff of the user! ", e);
		}
		
	}


	@Override
	public boolean checkActiveTariff(int userID) throws ValidateException, ServiceException {
		if (userID <= 0) {
			throw new ValidateException("ID of user is less or is equal to 0!");
		}
		
		DAOFactory daoObjectFactory = DAOFactory.getInstance();
		UserDAO daoUser = daoObjectFactory.getUserDAO();

		try {
			int count = daoUser.countActiveTariffs(userID);
			if (count > 0) {
				return true;
			}
		} catch (DAOException e) {
			throw new ServiceException("Can't count active tariffs! ", e);
		}
		return false;
	}

}
