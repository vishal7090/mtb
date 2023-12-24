package com.mtb.booking.mtb.master.exception;

public class NoSuchMovieException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchMovieException() {
		super();
	}

	public NoSuchMovieException(String message) {
		super(message);
	}

	public NoSuchMovieException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public NoSuchMovieException(Throwable throwable) {
		super(throwable);
	}
}
