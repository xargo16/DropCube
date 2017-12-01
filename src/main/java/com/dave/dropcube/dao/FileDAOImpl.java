package com.dave.dropcube.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.dave.dropcube.entity.FileEntity;

@Repository
public class FileDAOImpl implements FileDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public void save(FileEntity file) {
		entityManager.persist(file);
	}

	public FileEntity getFile(int fileId) {
		return entityManager.find(FileEntity.class, fileId);
	}

	public List<FileEntity> getMultipleFiles(int[] filesIds) {
		List<FileEntity> files = new ArrayList();
		for (int id : filesIds) {
			FileEntity file = entityManager.find(FileEntity.class, id);
			files.add(file);
		}
		return files;
	}

	public List<FileEntity> getAllFiles(int userId) {
		List<FileEntity> files = entityManager.createNamedQuery("getAllFiles",
				FileEntity.class).getResultList();
		return files;
	}

	public void deleteFile(int fileId) {
		FileEntity file = entityManager.find(FileEntity.class, fileId);
		entityManager.remove(file);
	}

	@Override
	public void deleteMultipleFiles(int[] filesIds) {
		for (int id : filesIds) {
			FileEntity file = entityManager.find(FileEntity.class, id);
			entityManager.remove(file);
		}
	}
}
