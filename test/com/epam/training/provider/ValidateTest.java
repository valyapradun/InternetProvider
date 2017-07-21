package com.epam.training.provider;

import static org.junit.Assert.*;

import org.junit.Test;

import com.epam.training.provider.service.impl.Validate;

public class ValidateTest {
	private final static String ERROR_LATIN_SYMBOL = "- Your login has to contain Latin symbols, digits, hyphens and underlinings from 3 to 15 symbols long! ";
		
	@Test
	public void testCheckLatinSymbolCorrect() {
		assertTrue("Wrong Validate.checkLatinSymbolCorrect()!", (Validate.checkLatinSymbol("abc123абв")).equals(ERROR_LATIN_SYMBOL));
	}
	
	
	@Test
	public void testCheckLatinSymbolNotCorrect() {
		assertFalse("Wrong Validate.checkLatinSymbolCorrect()!", (Validate.checkLatinSymbol("abc123абв")).equals(""));
	}
	
}
