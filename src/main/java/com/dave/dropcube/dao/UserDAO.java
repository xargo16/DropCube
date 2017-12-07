package com.dave.dropcube.dao;

import com.dave.dropcube.entity.UserEntity;

public interface UserDAO {
	public void register(UserEntity user);

	public UserEntity login(String email, String password);

}
