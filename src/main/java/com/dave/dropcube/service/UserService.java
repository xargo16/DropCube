package com.dave.dropcube.service;

import com.dave.dropcube.entity.User;

public interface UserService {

	public void register(User user);

	public User login(String email, String password);
}
