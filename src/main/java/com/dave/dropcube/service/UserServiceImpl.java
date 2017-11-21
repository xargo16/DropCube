package com.dave.dropcube.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.UserDAO;
import com.dave.dropcube.entity.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO userDAO;

	@Transactional
	public void register(User user) {
		userDAO.save(user);
	}

	@Transactional(readOnly=true)
	public User login(String email, String password) {
		return userDAO.login(email, password);
	}

}
