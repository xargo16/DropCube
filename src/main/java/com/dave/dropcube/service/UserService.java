package com.dave.dropcube.service;

import com.dave.dropcube.entity.User;
import com.dave.dropcube.entity.UserFile;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

public interface UserService {

	public void register(User user) throws InvalidRegistrationDataException;

	public User login(String email, String password);
	
	public UserFile[] getUserFiles(User user);
}
