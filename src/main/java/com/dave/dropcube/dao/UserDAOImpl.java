package com.dave.dropcube.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dave.dropcube.entity.UserEntity;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void register(UserEntity user) {
		entityManager.persist(user);
	}

	public UserEntity login(String email, String password) {
		Query query = entityManager.createNamedQuery("login");
		query.setParameter("email", email);
		query.setParameter("password", password);

		UserEntity user = null;
		try {
			Object object = query.getSingleResult();
			user = (UserEntity) object;
		} catch (NoResultException n) {
			/**
			 * Making sure that when there is no matching result ugly exception
			 * won't be thrown in a web browser
			 */
		}

		return user;
	}

}
