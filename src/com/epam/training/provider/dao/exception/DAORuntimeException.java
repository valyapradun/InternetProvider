package com.epam.training.provider.dao.exception;
/**
 * Class DAORuntimeException.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class DAORuntimeException extends RuntimeException {

	private static final long serialVersionUID = 5162263907178353424L;

	public DAORuntimeException() {
		super();
	}

	public DAORuntimeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DAORuntimeException(String arg0) {
		super(arg0);
	}

	public DAORuntimeException(Throwable arg0) {
		super(arg0);
	}
}
