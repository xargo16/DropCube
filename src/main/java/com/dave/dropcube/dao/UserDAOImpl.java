package com.dave.dropcube.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dave.dropcube.entity.User;

/*
 * Implementation of UserDAO which is using JPA's 
 * EntityManager to perform CRUD operations.
 */
@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(User user) {
		entityManager.persist(user);
	}

	public User login(String email, String password) {
		return null;
	}

}
