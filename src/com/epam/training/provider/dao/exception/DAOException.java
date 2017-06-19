package com.epam.training.provider.dao.exception;

public class DAOException extends Exception {
	private static final long serialVersionUID = 6522552330779658380L;

	public DAOException() {
		super();
	}

	public DAOException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DAOException(String arg0) {
		super(arg0);
	}

	public DAOException(Throwable arg0) {
		super(arg0);
	}
	
}