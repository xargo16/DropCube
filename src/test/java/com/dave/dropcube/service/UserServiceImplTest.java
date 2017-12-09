package com.dave.dropcube.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.dave.dropcube.dao.UserDAO;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	@Mock
	UserDAO userDAO;
	
	@InjectMocks
	UserServiceImpl userService = new UserServiceImpl();
	
	@Test(expected = InvalidRegistrationDataException.class)
	public void When_RegisteringUserEntityWithEmptyFirstName_Expect_InvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345678");

		userService.register(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_RegisteringUserEntityWithEmptyLastName_Expect_InvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("");
		user.setEmail("email@email");
		user.setPassword("12345678");

		userService.register(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_RegisteringUserEntityWithEmptyEmail_Expect_InvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("");
		user.setPassword("12345678");

		userService.register(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_RegisteringUserEntityWithEmailWithoutAtCharacter_Expect_InvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email");
		user.setPassword("12345678");

		userService.register(user);
	}

	@Test(expected = InvalidRegistrationDataException.class)
	public void When_RegisteringUserEntityWithPasswordShorterThan8Characters_Expect_InvalidRegistrationDataException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345");

		userService.register(user);
	}

	@Test
	public void When_RegisteringUserEntityWithFilledNecessaryPropertiesCorrectly_Expect_NoException()
			throws InvalidRegistrationDataException {
		UserEntity user = new UserEntity();
		user.setFirstName("Dawid");
		user.setLastName("Burdun");
		user.setEmail("email@email");
		user.setPassword("12345678");

		userService.register(user);
	}
}
