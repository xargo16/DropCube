package com.dave.dropcube.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.FileDAO;
import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.ForbiddenFileAccessException;
import com.dave.dropcube.util.MultipleFileEntitiesToZipConverter;
import com.dave.dropcube.util.FilenameDuplicationResolver;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDAO fileDAO;

	@Transactional
	public void uploadFile(FileEntity file) {
		List<FileEntity> allUserFiles = getAllFiles(file.getUserEntity());
		FilenameDuplicationResolver filenameDuplicationResolver = new FilenameDuplicationResolver();
		filenameDuplicationResolver.changeFilenameIfOneIsAlreadyPresent(
				allUserFiles, file);
		fileDAO.save(file);
	}

	@Transactional
	public List<FileEntity> getAllFiles(UserEntity user) {
		return fileDAO.getAllFiles(user);
	}

	@Transactional
	public FileEntity getFile(UserEntity user, int fileId) {
		FileEntity file = fileDAO.getFile(fileId);
		throwForbiddenFileAccessExceptionIfFileDoesNotBelongToUser(user, file);
		return file;
	}

	@Transactional
	public FileEntity getMultipleFiles(UserEntity user, int[] filesIds)
			throws IOException {
		List<FileEntity> files = fileDAO.getMultipleFiles(filesIds);
		MultipleFileEntitiesToZipConverter converter = new MultipleFileEntitiesToZipConverter();
		for (FileEntity file : files) {
			throwForbiddenFileAccessExceptionIfFileDoesNotBelongToUser(user,
					file);
		}
		FileEntity file = converter
				.packMultipleFileEntityObjectsInOneFileEntity(files);
		return file;
	}

	@Transactional
	public void deleteFile(UserEntity user, int fileId) {
		FileEntity file = fileDAO.getFile(fileId);
		throwForbiddenFileAccessExceptionIfFileDoesNotBelongToUser(user, file);
		fileDAO.deleteFile(fileId);
	}

	@Transactional
	public void deleteMultipleFiles(UserEntity user, int[] filesIds) {
		for (int fileId : filesIds) {
			FileEntity file = fileDAO.getFile(fileId);
			throwForbiddenFileAccessExceptionIfFileDoesNotBelongToUser(user,
					file);
		}

		fileDAO.deleteMultipleFiles(filesIds);
	}

	@Transactional
	public FileEntity getFileBasedOnUrlResource(UserEntity user, String url)
			throws IOException {
		if (urlResourcePointsToMultipleFiles(url)) {
			int[] fileIds = convertIdsFromUrlResourceToIntArray(url);
			return getMultipleFiles(user, fileIds);

		} else {
			return getFile(user, Integer.parseInt(url));
		}
	}

	@Transactional
	public void deleteFileBasedOnUrlResource(UserEntity user, String url) {
		if (urlResourcePointsToMultipleFiles(url)) {
			int[] fileIds = convertIdsFromUrlResourceToIntArray(url);
			deleteMultipleFiles(user, fileIds);
		} else {
			deleteFile(user, Integer.parseInt(url));
		}
	}

	private boolean urlResourcePointsToMultipleFiles(String urlPathVariable) {
		String regexForDetectingMultipleFilesDownloadRequest = "\\d+,.+";
		return urlPathVariable
				.matches(regexForDetectingMultipleFilesDownloadRequest);
	}

	private int[] convertIdsFromUrlResourceToIntArray(String url) {
		String[] stringFileIds = url.split(",");
		int[] intFileIds = new int[stringFileIds.length];

		for (int i = 0; i < stringFileIds.length; i++) {
			intFileIds[i] = Integer.parseInt(stringFileIds[i]);
		}
		return intFileIds;
	}

	private void throwForbiddenFileAccessExceptionIfFileDoesNotBelongToUser(
			UserEntity user, FileEntity file) {
		if (!(file.getUserEntity().getUserId() == user.getUserId()))
			throw new ForbiddenFileAccessException();
	}
}
