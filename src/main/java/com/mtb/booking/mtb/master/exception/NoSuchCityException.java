package com.mtb.booking.mtb.master.exception;

public class NoSuchCityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchCityException() {
		super();
	}

	public NoSuchCityException(String message) {
		super(message);
	}

	public NoSuchCityException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoSuchCityException(Throwable throwable) {
		super(throwable);
	}
}
