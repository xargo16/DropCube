package com.dave.dropcube.service;

import java.io.IOException;
import java.util.List;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;

public interface FileService {
	public void uploadFile(FileEntity file);

	public FileEntity getFile(UserEntity user, int fileId);

	public FileEntity getFileBasedOnUrlResource(UserEntity user, String url)
			throws IOException;

	public FileEntity getMultipleFiles(UserEntity user, int[] listOfIds)
			throws IOException;

	public List<FileEntity> getAllFiles(UserEntity user);

	public void deleteFile(UserEntity user, int fileId);

	public void deleteFileBasedOnUrlResource(UserEntity user, String url);

	public void deleteMultipleFiles(UserEntity user, int[] listOfIds);
}
