package com.dave.dropcube.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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

	/**
	 * This method uses JPQL to find user from database by its email and
	 * password
	 */
	public User login(String email, String password) {
		Query query = entityManager.createNamedQuery("login");
		query.setParameter("email", email);
		query.setParameter("password", password);

		User user = null;
		try {
			Object object = query.getSingleResult();
			user = (User) object;
		} catch (NoResultException n) {
			/**
			 * Making sure that when there is no matching result ugly exception
			 * won't be thrown in a web browser
			 */
		}

		return user;
	}

}
