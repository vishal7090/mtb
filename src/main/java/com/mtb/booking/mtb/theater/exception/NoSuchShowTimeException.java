package com.mtb.booking.mtb.theater.exception;

public class NoSuchShowTimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchShowTimeException() {
		super();
	}

	public NoSuchShowTimeException(String message) {
		super(message);
	}

	public NoSuchShowTimeException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoSuchShowTimeException(Throwable throwable) {
		super(throwable);
	}
}
