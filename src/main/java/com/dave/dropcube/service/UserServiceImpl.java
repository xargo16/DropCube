package com.dave.dropcube.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.UserDAO;
import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.InvalidRegistrationDataException;
import com.dave.dropcube.util.RegistrationFormValidator;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	FileService fileService;

	@Transactional
	public void register(UserEntity user) throws InvalidRegistrationDataException {
		RegistrationFormValidator registrationFormValidator = new RegistrationFormValidator();

		assignRoleToUser(user, UserEntity.Role.USER);
		registrationFormValidator.validateRegistrationFormData(user);

		userDAO.save(user);
	}

	private void assignRoleToUser(UserEntity user, UserEntity.Role role) {
		user.setRole(role);
	}

	@Transactional(readOnly = true)
	public UserEntity login(String email, String password) {
		return userDAO.login(email, password);
	}

	public List<FileEntity> getUserFiles(UserEntity user) {
		return fileService.getAllFiles(user.getUserId());
	}

}
