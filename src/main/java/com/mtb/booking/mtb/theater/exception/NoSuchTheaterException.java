package com.mtb.booking.mtb.theater.exception;

public class NoSuchTheaterException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchTheaterException() {
		super();
	}

	public NoSuchTheaterException(String message) {
		super(message);
	}

	public NoSuchTheaterException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoSuchTheaterException(Throwable throwable) {
		super(throwable);
	}
}
