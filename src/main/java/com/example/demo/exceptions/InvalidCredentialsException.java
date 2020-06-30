package com.example.demo.exceptions;

public class InvalidCredentialsException extends Exception {

	/**
	 * serialid
	 */
	private static final long serialVersionUID = 1L;

	String message;

	public InvalidCredentialsException(String message) {
		super(message);
		this.message = message;
	}

	public InvalidCredentialsException() {
		super();
	}
	
}
