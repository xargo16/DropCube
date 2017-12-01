package com.dave.dropcube.service;

import java.io.IOException;
import java.util.List;

import com.dave.dropcube.entity.FileEntity;

public interface FileService {
	public void uploadFile(FileEntity file);

	public FileEntity getFile(int fileId);

	public FileEntity getMultipleFiles(int[] listOfIds) throws IOException;

	public List<FileEntity> getAllFiles(int userId);

	public void deleteFile(int fileId);
	
	public void deleteMultipleFiles(int[] listOfIds);
}
