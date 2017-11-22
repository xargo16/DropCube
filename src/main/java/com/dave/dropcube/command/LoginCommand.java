package com.dave.dropcube.command;

/*
 * POJO class used for binding login form parameters to it in UserController
 */
public class LoginCommand {
	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
