package com.dave.dropcube.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dave.dropcube.dao.FileDAO;
import com.dave.dropcube.entity.FileEntity;

@Service
public class FileServiceImpl implements FileService {
	@Autowired
	private FileDAO fileDAO;

	@Transactional
	public void uploadFile(FileEntity file) {
		changeFilenameIfOneIsAlreadyPresent(file);
		fileDAO.save(file);
	}

	private void changeFilenameIfOneIsAlreadyPresent(FileEntity file) {
		List<FileEntity> listOfFiles = getAllFiles(1);

		for (FileEntity tempFile : listOfFiles) {
			if (tempFile.getName().equals(file.getName())) {

				String newFilename = getFilenameWithNumberSuffixInParenthesis(file
						.getName());
				file.setName(newFilename);

				changeFilenameIfOneIsAlreadyPresent(file);
			}
		}
	}

	private String getFilenameWithNumberSuffixInParenthesis(String filename) {
		String newFilename = null;
		if (filenameHasAlreadyNumberInParenthesisSuffix(filename)) {
			newFilename = getNewFilenameWithIncrementedNumberInParenthesisSuffix(filename);
		} else {
			newFilename = getNewFilenameWithNumberOneInParenthesisSuffix(filename);
		}
		return newFilename;
	}

	private boolean filenameHasAlreadyNumberInParenthesisSuffix(String filename) {
		String regexForMatchingFilenameWithNumberInParenthesisSuffix = "(.+)(\\((\\d+)\\))\\..+";
		return filename
				.matches(regexForMatchingFilenameWithNumberInParenthesisSuffix);
	}

	private String getNewFilenameWithIncrementedNumberInParenthesisSuffix(
			String filename) {
		String newFilename = null;
		String regexForGroupingFilenameWithNumberInParenthesisSuffixAndExtension = "(.+)(\\((\\d+)\\))(\\..+)";
		Pattern pattern = Pattern
				.compile(regexForGroupingFilenameWithNumberInParenthesisSuffixAndExtension);
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {
			String numberInParenthesis = matcher.group(3);
			String newNumberInParenthesis = ""
					+ (Integer.parseInt(numberInParenthesis) + 1);

			newFilename = matcher.group(1) + "(" + newNumberInParenthesis + ")"
					+ matcher.group(4);

		}
		return newFilename;
	}

	private String getNewFilenameWithNumberOneInParenthesisSuffix(
			String filename) {
		String newFilename = null;
		String regexForGroupingFilenameAndExtension = "(.+)(\\..+)";
		Pattern pattern = Pattern.compile(regexForGroupingFilenameAndExtension);
		Matcher matcher = pattern.matcher(filename);
		if (matcher.find()) {
			newFilename = matcher.group(1) + "(1)" + matcher.group(2);
		}
		return newFilename;
	}

	@Transactional
	public FileEntity getFile(int fileId) {
		return fileDAO.getFile(fileId);
	}

	/**
	 * Method used when user decides to download multiple files. It takes list
	 * of ids of files, get those files from data source, takes their content
	 * and pack it in ZipOutputStream backed by ByteArrayOutputStream and
	 * finally wraps bytes from zipped ByteArrayOutputStream with FileEntity
	 */
	@Transactional
	public FileEntity getMultipleFiles(int[] filesIds) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		List<FileEntity> files = fileDAO.getMultipleFiles(filesIds);
		
		try (ZipOutputStream out = new ZipOutputStream(byteOut)) {
			for (FileEntity file : files) {
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
	public List<FileEntity> getAllFiles(int userId) {
		return fileDAO.getAllFiles(userId);
	}

	@Transactional
	public void deleteFile(int fileId) {
		fileDAO.deleteFile(fileId);
	}

	@Transactional
	public void deleteMultipleFiles(int[] filesIds) {
		fileDAO.deleteMultipleFiles(filesIds);
	}
}
