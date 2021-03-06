 package com.epam.training.provider.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.epam.training.provider.command.impl.*;
/**
 * Final class with the list of action's commands for pattern Command .
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public final class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();
	private final static Logger logger = LogManager.getLogger(CommandProvider.class.getName());

	public CommandProvider() {
		repository.put(CommandName.ADD_PAYMENT, new AddPaymentCommand());
		repository.put(CommandName.ADD_TARIFF, new AddTariffCommand());
		repository.put(CommandName.BUY_TARIFF, new BuyTariffCommand());
		repository.put(CommandName.CHOOSE_LANGUAGE, new ChooseLanguageCommand());
		repository.put(CommandName.DELETE_TARIFF, new DeleteTariffCommand());
		repository.put(CommandName.DELETE_USER, new DeleteUserCommand());
		repository.put(CommandName.DISPLAY_TARIFFS, new DisplayTariffsCommand());
		repository.put(CommandName.DISPLAY_USERS, new DisplayUsersCommand());
		repository.put(CommandName.EDIT_TARIFF, new EditTariffCommand());
		repository.put(CommandName.END_TARIFF, new EndTariffCommand());
		repository.put(CommandName.PROLONG_UNLIM_TARIFFS, new ProlongUnlimTariffsCommand());
		repository.put(CommandName.PUT_BAN, new PutBanCommand());
		repository.put(CommandName.REGISTRATION, new RegistrationCommand());
		repository.put(CommandName.REMOVE_BAN, new RemoveBanCommand());
		repository.put(CommandName.SEARCH_ONE_TARIFF, new SearchOneTariffCommand());
		repository.put(CommandName.SEARCH_TARIFFS, new SearchTariffsCommand());
		repository.put(CommandName.SEARCH_USER_PAYMENTS, new SearchUserPaymentCommand());
		repository.put(CommandName.SHOW_ADMIN_PAGE, new ShowAdminPageCommand());
		repository.put(CommandName.SHOW_INDEX_PAGE, new ShowIndexPageCommand());
		repository.put(CommandName.SHOW_USER_PAGE, new ShowUserPageCommand());
		repository.put(CommandName.SHOW_USER_TARIFFS, new ShowUserTariffsCommand());
		repository.put(CommandName.SIGN_IN, new SignInCommand());
		repository.put(CommandName.SIGN_OUT, new SignOutCommand());
		repository.put(CommandName.WRONG_REQUEST, new WrongRequestCommand());
	}

	
/**
* Method for search of implementation of specific action-command.
* 
* @param name action-command {@link String}
* @return implementation of command {@link Command}
*           
*/
	public Command getCommand(String name) {
		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			command = repository.get(CommandName.WRONG_REQUEST);
			logger.log(Level.ERROR, "Wrong request: " + e);
		}
		return command;
	}

}
