package com.dave.dropcube.command;

import com.dave.dropcube.entity.User;

/*
 * POJO class used for binding register form parameters to it in UserController
 */
public class UserCommand {
	/*
	 * We're just using our entity, because it contains exactly the same fields
	 * as the register form
	 */
	User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
