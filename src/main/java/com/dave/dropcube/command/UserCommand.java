package com.dave.dropcube.command;

import com.dave.dropcube.entity.UserEntity;

/*
 * POJO class used for binding register form parameters to it in UserController
 */
public class UserCommand {
	/*
	 * We're just using our entity, because it contains exactly the same fields
	 * as the register form
	 */
	UserEntity user;

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
