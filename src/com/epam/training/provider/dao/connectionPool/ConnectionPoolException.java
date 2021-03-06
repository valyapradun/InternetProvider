package com.epam.training.provider.dao.connectionPool;
/**
 * Class ConnectionPoolException.
 * 
 * @author Valentina Pradun
 * @version 1.0
 */
public class ConnectionPoolException extends Exception {
	private static final long serialVersionUID = -4804741111910589521L;

	public ConnectionPoolException() {
		super();
	}

	public ConnectionPoolException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConnectionPoolException(String message) {
		super(message);
	}

	public ConnectionPoolException(Throwable cause) {
		super(cause);
	}
	
	
	

}
