package com.dave.dropcube.service;

import java.util.List;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.InvalidRegistrationDataException;

public interface UserService {

	public void register(UserEntity user) throws InvalidRegistrationDataException;

	public UserEntity login(String email, String password);
	
	public List<FileEntity> getUserFiles(UserEntity user);
}
