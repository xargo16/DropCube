package com.dave.dropcube.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@NamedQuery(query = "SELECT u FROM UserEntity u WHERE u.email = :email AND u.password = :password", name = "login")
public class UserEntity {
	public enum Role {
		USER, ADMIN;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(name = "role")
	private Role role = Role.USER;

	@Column(name = "premium_account")
	private boolean premiumAccount = false;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isPremiumAccount() {
		return premiumAccount;
	}

	public void setPremiumAccount(boolean premiumAccount) {
		this.premiumAccount = premiumAccount;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", password=" + password
				+ ", email=" + email + ", role=" + role + ", premiumAccount="
				+ premiumAccount + "]";
	}

}
