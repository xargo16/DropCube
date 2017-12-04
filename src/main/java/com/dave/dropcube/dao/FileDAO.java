package com.dave.dropcube.dao;

import java.util.List;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;

public interface FileDAO {
	public void save(FileEntity file);

	public FileEntity getFile(int fileId);

	public List<FileEntity> getMultipleFiles(int[] filesIds);

	public List<FileEntity> getAllFiles(UserEntity user);

	public void deleteFile(int fileId);
	
	public void deleteMultipleFiles(int[] filesIds);
}
