package com.mtb.booking.mtb.theater.exception;

public class NoSuchScreenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchScreenException() {
		super();
	}

	public NoSuchScreenException(String message) {
		super(message);
	}

	public NoSuchScreenException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoSuchScreenException(Throwable throwable) {
		super(throwable);
	}
}
