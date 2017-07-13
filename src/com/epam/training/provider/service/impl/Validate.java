package com.epam.training.provider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

	private static final String LATIN_SYMBOL_PATTERN = "^[A-Za-z0-9_-]{3,15}$";
	private static final String DIFFICULT_PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	private static final String VALID_EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
//	private static final String DIGIT_PATTERN = "^[ 0-9]+$";

	private final static String ERROR_LATIN_SYMBOL = "- Your login has to contain Latin symbols, digits, hyphens and underlinings from 3 to 15 symbols long! ";
	private final static String ERROR_DIFFICULT_PASSWORD = "- Your password has to contain Latin symbols in the upper and lower case and digits from 6 to 20 symbols long! ";
	private final static String ERROR_VALID_EMAIL = "- Your email has to contain Latin symbols, digits, '@' and '.'! ";
	
	
	public static String checkLatinSymbol(String login) {
		String result = "";
		Pattern pattern = Pattern.compile(LATIN_SYMBOL_PATTERN);
		Matcher matcher = pattern.matcher(login);
		if (!matcher.matches()) {
			result = ERROR_LATIN_SYMBOL;
		}
		return result;

	}

	public static String checkDifficultPassword(String password) {
		String result = "";
		Pattern pattern = Pattern.compile(DIFFICULT_PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		if (!matcher.matches()) {
			result = ERROR_DIFFICULT_PASSWORD;
		}
		return result;

	}

	public static String checkValidEmail(String email) {
		String result = "";
		Pattern pattern = Pattern.compile(VALID_EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);
		if (!matcher.matches()) {
			result = ERROR_VALID_EMAIL;
        }
		return result;
		
	}

}
