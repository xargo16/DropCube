package com.dave.dropcube.exception;

/*
 * This exception is thrown when data provided in registration form is invalid in some way
 * (email = 123 etc.)
 */
public class InvalidRegistrationDataException extends Exception {

	public InvalidRegistrationDataException() {

	}

	public InvalidRegistrationDataException(String msg) {
		super(msg);
	}
}
