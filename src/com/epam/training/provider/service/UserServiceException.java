package com.epam.training.provider.service;

public class UserServiceException extends Exception {
	private static final long serialVersionUID = -6907365504938090725L;

	public UserServiceException() {
		super();
	}

	public UserServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserServiceException(String message) {
		super(message);
	}

	public UserServiceException(Throwable cause) {
		super(cause);
	}
	
	

}
