package com.dave.dropcube.util;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

public class RegistrationFormValidatorTest {
	RegistrationFormValidator validator = new RegistrationFormValidator();

	@Test
	public void When_FirstNameIsEmpty_Expect_IsFirstNameValidAsFalse() {
		String firstName = "";
		boolean firstNameValid = validator.isFirstNameValid(firstName);
		assertThat(firstNameValid, is(false));
	}

	@Test
	public void When_LastNameIsEmpty_Expect_IsLastNameValidAsFalse() {
		String lastName = "";
		boolean lastNameValid = validator.isLastNameValid(lastName);
		assertThat(lastNameValid, is(false));
	}

	@Test
	public void When_EmailIsEmpty_Expect_IsEmailValidAsFalse() {
		String email = "";
		boolean emailValid = validator.isEmailValid(email);
		assertThat(emailValid, is(false));
	}

	@Test
	public void When_EmailDoesNotHaveAtCharacter_Expect_IsEmailValidAsFalse() {
		String email = "emailgmail.com";
		boolean emailValid = validator.isEmailValid(email);
		assertThat(emailValid, is(false));
	}

	@Test
	public void When_EmailContainsAtCharacter_Expect_IsEmailValidAsTrue() {
		String email = "email@gmail.com";
		boolean emailValid = validator.isEmailValid(email);
		assertThat(emailValid, is(true));
	}

	@Test
	public void When_PasswordIsShorterThan8Characters_Expect_IsPasswordValidAsFalse() {
		String password = "1234567";
		boolean passwordValid = validator.isPasswordValid(password);
		assertThat(passwordValid, is(false));
	}

	@Test
	public void When_PasswordIsEqualOrLongerThan8Characters_Expect_IsPasswordValidAsTrue() {
		String password = "12345678";
		String password2 = "123456789";
		boolean passwordValid = validator.isPasswordValid(password);
		boolean password2Valid = validator.isPasswordValid(password2);

		assertThat(passwordValid, is(true));
		assertThat(password2Valid, is(true));
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_UserEntityHasGotEmptyFirstName_Expect_ThrowInvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345678");

		validator.validateRegistrationFormData(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_UserEntityHasGotEmptyLastName_Expect_ThrowInvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("");
		user.setEmail("email@email");
		user.setPassword("12345678");

		validator.validateRegistrationFormData(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_UserEntityHasGotEmptyEmail_Expect_ThrowInvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("");
		user.setPassword("12345678");

		validator.validateRegistrationFormData(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_UserEntityHasGotEmailWithoutAtCharacter_Expect_ThrowInvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email");
		user.setPassword("12345678");

		validator.validateRegistrationFormData(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_UserEntityHasGotPasswordShorterThan8Characters_Expect_ThrowInvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345");

		validator.validateRegistrationFormData(user);
	}

	@Test
	public void When_UserEntityHasGotFilledNecessaryPropertiesCorrectly_Expect_NoException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345678");

		validator.validateRegistrationFormData(user);
	}
}
