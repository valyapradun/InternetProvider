package com.epam.training.provider.command;
/**
 * Enum with the list of action's commands for pattern Command .
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public enum CommandName {
	ADD_PAYMENT,
	ADD_TARIFF, 
	BUY_TARIFF,
	CHOOSE_LANGUAGE,
	DELETE_TARIFF, 
	DELETE_USER,
	DISPLAY_TARIFFS, 
	DISPLAY_USERS, 
	EDIT_TARIFF,
	END_TARIFF,
	PROLONG_UNLIM_TARIFFS,
	PUT_BAN,
	REGISTRATION,
	REMOVE_BAN,
	SEARCH_ONE_TARIFF,
	SEARCH_TARIFFS, 
	SEARCH_USER_PAYMENTS,
	SHOW_ADMIN_PAGE,
	SHOW_INDEX_PAGE, 
	SHOW_USER_PAGE,
	SHOW_USER_TARIFFS,
	SIGN_IN, 
	SIGN_OUT, 
	WRONG_REQUEST;
}
