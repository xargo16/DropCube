package com.dave.dropcube.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.FileDAO;
import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.ForbiddenFileAccessException;
import com.dave.dropcube.util.FilenameDuplicationResolver;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDAO fileDAO;

	@Transactional
	public void uploadFile(FileEntity file) {
		List<FileEntity> allUserFiles = getAllFiles(file.getUserEntity());
		FilenameDuplicationResolver filenameDuplicationResolver = new FilenameDuplicationResolver();
		filenameDuplicationResolver.changeFilenameIfOneIsAlreadyPresent(allUserFiles, file);
		fileDAO.save(file);
	}


	@Transactional
	public FileEntity getFile(UserEntity userEntity, int fileId) {
		FileEntity file = fileDAO.getFile(fileId);
		if (!fileMatchesUser(userEntity, file))
			throw new ForbiddenFileAccessException();
		return file;
	}

	/**
	 * Method used when user decides to download multiple files. It takes list
	 * of ids of files, get those files from data source, takes their content
	 * and pack it in ZipOutputStream backed by ByteArrayOutputStream and
	 * finally wraps bytes from zipped ByteArrayOutputStream with FileEntity
	 */
	@Transactional
	public FileEntity getMultipleFiles(UserEntity userEntity, int[] filesIds)
			throws IOException {
		List<FileEntity> files = fileDAO.getMultipleFiles(filesIds);

		FileEntity file = packMultipleFilesInZipFile(userEntity, files);
		return file;
	}

	private FileEntity packMultipleFilesInZipFile(UserEntity user,
			List<FileEntity> files) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		try (ZipOutputStream out = new ZipOutputStream(byteOut)) {
			for (FileEntity file : files) {
				if (!fileMatchesUser(user, file))
					throw new ForbiddenFileAccessException();

				out.putNextEntry(new ZipEntry(file.getName()));
				out.write(file.getContent());
				out.closeEntry();
			}
		} catch (IOException e) {
			throw e;
		}

		FileEntity file = new FileEntity();
		file.setName("DropCube.zip");
		file.setContent(byteOut.toByteArray());
		return file;
	}

	

	@Transactional
	public List<FileEntity> getAllFiles(UserEntity user) {
		return fileDAO.getAllFiles(user);
	}

	@Transactional
	public void deleteFile(UserEntity user, int fileId) {
		FileEntity file = fileDAO.getFile(fileId);
		
		if(!fileMatchesUser(user, file))
			throw new ForbiddenFileAccessException();
		
		fileDAO.deleteFile(fileId);
	}

	@Transactional
	public void deleteMultipleFiles(UserEntity user, int[] filesIds) {
		for(int fileId : filesIds){
			FileEntity file = fileDAO.getFile(fileId);
			if(!fileMatchesUser(user, file))
				throw new ForbiddenFileAccessException();
		}
		
		
		fileDAO.deleteMultipleFiles(filesIds);
	}
	
	private boolean fileMatchesUser(UserEntity user, FileEntity file) {
		return file.getUserEntity().getUserId() == user.getUserId();
	}
}
