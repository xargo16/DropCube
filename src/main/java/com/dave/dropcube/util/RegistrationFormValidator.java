package com.dave.dropcube.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.dave.dropcube.entity.User;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

/**
 * This class is used to perform validation of registration form provided by the
 * user. InvalidRegistrationDataException is thrown when any data is corrupted
 * 
 * @author Dawid Burdun
 * 
 */
public class RegistrationFormValidator {
	/**
	 * This list contains messages in regards to invalid data present in
	 * registration form
	 */
	private List<String> errors = new ArrayList<String>();

	public void validateRegistrationFormData(User user)
			throws InvalidRegistrationDataException {
		checkFirstName(user.getFirstName());
		checkLastName(user.getLastName());
		checkEmail(user.getEmail());
		checkPassword(user.getPassword());

		if (errors.size() > 0) {
			String errorMessage = "You provided invalid ";
			for (int i = 0; i < errors.size(); i++) {
				errorMessage += errors.get(i);
				if (i < errors.size() - 1) // To prevent adding comma after last error
					errorMessage += ", ";
				else
					errorMessage += "!";
			}
			throw new InvalidRegistrationDataException(errorMessage);
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
		String emailRegex = "[a-zA-Z0-9._%-+]+@[a-zA-Z0-9-.]+";

		if (email.length() == 0 || !email.matches(emailRegex))
			errors.add("email");

	}

	private void checkPassword(String password) {
		if (password.length() == 0) {
			errors.add("password");
		}
	}
}
