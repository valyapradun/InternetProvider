package com.epam.training.provider.service.exception;
/**
 * Class ServiceException.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = -4260678712180444586L;

	public ServiceException() {
		super();
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
