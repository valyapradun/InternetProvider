package com.epam.training.provider.util;

public final class Permanent {

	/* for actions */
	public static final String ACTION = "action";
	public static final String ACTION_DISPLAY_TARIFFS = "?action=display_tariffs";
	public static final String ACTION_DISPLAY_USERS = "?action=display_users";
	public static final String ACTION_SHOW_ADMIN_PAGE = "?action=show_admin_page";
	public static final String ACTION_SHOW_USER_PAGE = "?action=show_user_page";
	public static final String ACTION_REGISTRATION = "?action=sign_in";
	public static final String ACTION_SHOW_INDEX_PAGE = "show_index_page";
	public static final String ACTION_SHOW_USER_TARIFFS = "?action=show_user_tariffs";

	/* for attribute/parameters of jsp-page */
	public final static String ERROR_MESSAGE = "error";
	public final static String INFO_MESSAGE = "info";
	public final static String USER = "user";
	public static final String REDIRECT_PARAMETER = "redirectTo";
	public static final String LANGUAGE = "lang";
	public static final String REFERER = "referer";
	public static final String OK = "Yes";
	
	/* for user */
	public final static String USER_ID = "id";
	public final static String USER_LOGIN = "login";
	public final static String USER_PASSWORD = "password";
	public final static String USER_NAME = "name";
	public final static String USER_EMAIL = "email";

	/* for tariff */
	public final static String TARIFF_ID = "id";
	public final static String TARIFF_NAME = "name";
	public final static String TARIFF_PRICE = "price";
	public final static String TARIFF_SIZE = "size";
	public final static String TARIFF_SPEED = "speed";
	public final static String TARIFF_TYPE = "type";
	public final static String TARIFF_PICTURE = "picture";
	
	/* for transaction */
	public final static String PAYMENT_AMOUNT = "amount";
	
	
	/* pages */
	public static final String INDEX_PAGE = "/index.jsp";
	public static final String USER_PAGE = "/user_page.jsp";
	public static final String ADMIN_PAGE = "/admin_page.jsp";
	public static final String LIST_TARIFFS_PAGE = "/admin_tariff.jsp";
	public static final String TARIFF_PAGE = "/admin_card_tariff.jsp";
	public static final String LIST_USERS_PAGE = "/admin_user.jsp";
	public static final String LIST_PAYMENTS_PAGE = "/user_transaction.jsp";
	public static final String REGISTRATION_PAGE = "/register.jsp";
	public static final String CATALOG_PAGE = "/catalog.jsp";
	public static final String ERROR_PAGE = "/error.jsp";
	public static final String USER_TARIFFS_PAGE = "/user_tariff.jsp";

	
	/* for filter */
	public static final String[][] SECURITY_ACTION = {
			{   
				// for administrator
				"add_tariff", "delete_tariff", "display_tariffs", "display_users", "edit_tariff", "search_one_tariff", "show_admin_page" 
			},
			{   
				// for user
				"add_payment", "buy_tariff", "search_user_page", "search_user_payments", "show_user_tariffs"
			},
		};

	private Permanent() {

	}
}
