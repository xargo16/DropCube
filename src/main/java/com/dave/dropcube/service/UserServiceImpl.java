package com.dave.dropcube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.UserDAO;
import com.dave.dropcube.entity.User;
import com.dave.dropcube.exception.InvalidRegistrationDataException;
import com.dave.dropcube.util.RegistrationFormValidator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Transactional
	public void register(User user) throws InvalidRegistrationDataException {
		RegistrationFormValidator registrationFormValidator = new RegistrationFormValidator();

		assignRoleToUser(user, User.Role.USER);
		registrationFormValidator.validateRegistrationFormData(user);

		userDAO.save(user);
	}

	private void assignRoleToUser(User user, User.Role role) {
		user.setRole(role);
	}

	@Transactional(readOnly = true)
	public User login(String email, String password) {
		return userDAO.login(email, password);
	}

}
