package com.dave.dropcube.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;

@Repository
public class FileDAOImpl implements FileDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(FileEntity file) {
		entityManager.persist(file);
	}

	@Override
	public FileEntity getFile(int fileId) {
		return entityManager.find(FileEntity.class, fileId);
	}

	@Override
	public List<FileEntity> getMultipleFiles(int[] filesIds) {
		List<FileEntity> files = new ArrayList<FileEntity>();
		for (int id : filesIds) {
			FileEntity file = entityManager.find(FileEntity.class, id);
			files.add(file);
		}
		return files;
	}

	@Override
	public List<FileEntity> getAllFiles(UserEntity user) {
		Query query = entityManager.createNamedQuery("getAllFiles",
				FileEntity.class);
		query.setParameter("user", user);
		List<FileEntity> files = query.getResultList();
		return files;
	}

	@Override
	public void deleteFile(int fileId) {
		FileEntity file = entityManager.find(FileEntity.class, fileId);
		entityManager.remove(file);
	}
}
