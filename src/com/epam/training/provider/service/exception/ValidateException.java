package com.epam.training.provider.service.exception;
/**
 * Class ValidateException.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ValidateException extends Exception {

	private static final long serialVersionUID = 8956926478984018767L;

	public ValidateException() {
		super();
	}

	public ValidateException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidateException(String message) {
		super(message);
	}

	public ValidateException(Throwable cause) {
		super(cause);
	}

}
