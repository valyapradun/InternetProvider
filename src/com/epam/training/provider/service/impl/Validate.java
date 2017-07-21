package com.epam.training.provider.service.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

	private static final String LATIN_SYMBOL_PATTERN = "^[A-Za-z0-9_-]{3,15}$";
	private static final String DIFFICULT_PASSWORD_PATTERN = "((?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,20})";
	private static final String VALID_EMAIL_PATTERN = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$";
	private static final String TARIFF_NAME_PATTERN = "^[A-Za-zА-Яа-я0-9_-\\s]{3,45}$";
//	private static final String DIGIT_PATTERN = "^[ 0-9]+$";

	private final static String ERROR_LATIN_SYMBOL = "- Your login has to contain Latin symbols, digits, hyphens and underlinings from 3 to 15 symbols long! ";
	private final static String ERROR_DIFFICULT_PASSWORD = "- Your password has to contain Latin symbols in the upper and lower case and digits from 6 to 20 symbols long! ";
	private final static String ERROR_VALID_EMAIL = "- Your email has to contain Latin symbols, digits, '@' and '.'! ";
	private final static String ERROR_TARIFF_NAME = "- Your tariff's name has to contain Latin/Cyrillic symbols, digits, hyphens and underlinings from 3 to 45 symbols long! ";
	private final static String ERROR_REQUIRED_FIELD = "- This field is required! Fill it! ";
	
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
	
	public static String checkTariffName(String nameTariff) {
		String result = "";
		Pattern pattern = Pattern.compile(TARIFF_NAME_PATTERN);
		Matcher matcher = pattern.matcher(nameTariff);
		if (!matcher.matches()) {
			result = ERROR_TARIFF_NAME;
		}
		return result;

	}

	public static String checkRequiredStringField(String nameField, String valuefield){
		String result = "";
		if (valuefield == null || "".equals(valuefield) || valuefield.trim().length() == 0){
			result = nameField + ERROR_REQUIRED_FIELD;
		}
		return result;
	}
	
	public static String checkRequiredDoubleField(String nameField, double valuefield){
		String result = "";
		if (valuefield <= 0) {
			result = nameField + ERROR_REQUIRED_FIELD;
		}
		return result;
	}
	
}
