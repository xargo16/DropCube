package com.dave.dropcube.dao;

import com.dave.dropcube.entity.User;

public interface UserDAO {
	public void save(User user);

	public User login(String email, String password);

}
