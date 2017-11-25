package com.dave.dropcube.service;

import org.springframework.stereotype.Service;

import com.dave.dropcube.entity.UserFile;

public interface FileService {
	public void saveFile(UserFile file);

	public UserFile getFile(int fileId);

	public UserFile[] getAllFiles(int userId); //Get all files for specific user

	public void deleteFile(int fileId);

	public void deleteAllFiles(int userId); //Delete all files for specific user
}
