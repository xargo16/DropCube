package com.dave.dropcube.util;

import java.util.ArrayList;
import java.util.List;

import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

/*
 * This class is used to perform validation of registration form provided by the
 * user. InvalidRegistrationDataException is thrown when any data is corrupted
 */
public class RegistrationFormValidator {
	/**
	 * This list contains messages in regards to invalid data present in
	 * registration form
	 */
	private List<String> errors = new ArrayList<String>();
	
	private int passwordMinimumLength = 8;
	private String emailValidationRegex = "[a-zA-Z0-9._%-+]+@[a-zA-Z0-9-.]+";

	public void validateRegistrationFormData(UserEntity user)
			throws InvalidRegistrationDataException {
		checkFirstName(user.getFirstName());
		checkLastName(user.getLastName());
		checkEmail(user.getEmail());
		checkPassword(user.getPassword());

		if (errors.size() > 0) {
			throwInvalidRegistrationDataExceptionWithProperErrorMessage();
		}
	}

	private void checkFirstName(String firstName) {
		if (firstName.length() == 0)
			errors.add("first name");
	}

	private void checkLastName(String lastName) {
		if (lastName.length() == 0)
			errors.add("last name");
	}

	private void checkEmail(String email) {
		if (email.length() == 0 || !email.matches(emailValidationRegex))
			errors.add("email");

	}

	private void checkPassword(String password) {
		if (password.length() < passwordMinimumLength) {
			errors.add("password length");
		}
	}

	private void throwInvalidRegistrationDataExceptionWithProperErrorMessage()
			throws InvalidRegistrationDataException {
		String errorMessage = "You provided invalid ";
		for (int i = 0; i < errors.size(); i++) {
			errorMessage += errors.get(i);
			if (i < errors.size() - 1) // To prevent adding comma after last
										// error
				errorMessage += ", ";
			else
				errorMessage += "!";
		}
		throw new InvalidRegistrationDataException(errorMessage);
	}
}
