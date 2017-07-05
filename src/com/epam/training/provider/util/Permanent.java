package com.epam.training.provider.util;

public final class Permanent {

	/* for jsp */
	public static final String REDIRECT_PARAMETER = "redirectTo";
	public static final String ACTION = "action";
	public static final String ACTION_TARIFFS = "?action=tariffs";
	public final static String ERROR = "error";
	public final static String USER = "user";

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

	/* pages */
	public static final String INDEX = "/index.jsp";
	public static final String ADMIN_PAGE = "/admin_index.jsp";
	public static final String ADMIN_TARIFFS = "/admin_tariff.jsp";
	public static final String ADMIN_CARD_TARIFF = "/admin_card_tariff.jsp";
	public static final String USER_PAGE = "/user_main.jsp";
	public static final String CATALOG = "/catalog.jsp";
	public static final String ERROR_PAGE = "/error.jsp";

	private Permanent() {

	}
}
