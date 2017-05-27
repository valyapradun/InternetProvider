package com.epam.training.provider.dao;

public class UserDaoException extends Exception {
	private static final long serialVersionUID = -8975497752313650312L;

	public UserDaoException() {
		super();
	}

	public UserDaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public UserDaoException(String message) {
		super(message);
	}

	public UserDaoException(Throwable cause) {
		super(cause);
	}

}
