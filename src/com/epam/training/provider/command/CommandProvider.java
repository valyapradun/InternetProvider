package com.epam.training.provider.command;

import java.util.HashMap;
import java.util.Map;

import com.epam.training.provider.command.impl.*;

public final class CommandProvider {
	private final Map<CommandName, Command> repository = new HashMap<>();

	CommandProvider() {
		repository.put(CommandName.SIGN_IN, new SignInCommand());
		repository.put(CommandName.REGISTRATION, new RegCommand());
		repository.put(CommandName.SIGN_OUT, new SignOutCommand());
		repository.put(CommandName.INDEX, new IndexPageCommand());
		repository.put(CommandName.ALL_TARIFFS, new AllTariffCommand());
		repository.put(CommandName.WRONG_REQUEST, new WrongRequestCommand());
		repository.put(CommandName.TARIFFS, new TariffsCommand());
		repository.put(CommandName.CARD_TARIFF, new CardTariffCommand());
		repository.put(CommandName.EDIT_TARIFF, new EditTariffCommand());
		repository.put(CommandName.ADD_TARIFF, new AddTariffCommand());
		repository.put(CommandName.DELETE_TARIFF, new DeleteTariffCommand());
		repository.put(CommandName.USERS, new UsersCommand());
		repository.put(CommandName.PAY_ACT, new PayActCommand());
		repository.put(CommandName.REFILL, new RefillCommand());
		repository.put(CommandName.USER_MAIN, new UserMainCommand());
		repository.put(CommandName.ADMIN_MAIN, new AdminMainCommand());
	}

	Command getCommand(String name) {
		CommandName commandName = null;
		Command command = null;
		try {
			commandName = CommandName.valueOf(name.toUpperCase());
			command = repository.get(commandName);
		} catch (IllegalArgumentException | NullPointerException e) {
			// write log
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}

}
