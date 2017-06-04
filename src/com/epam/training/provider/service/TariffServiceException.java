package com.epam.training.provider.service;

public class TariffServiceException extends Exception {
	private static final long serialVersionUID = 1978057791969224357L;

	public TariffServiceException() {
		super();
	}

	public TariffServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public TariffServiceException(String message) {
		super(message);
	}

	public TariffServiceException(Throwable cause) {
		super(cause);
	}
}
