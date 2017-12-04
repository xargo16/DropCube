package com.dave.dropcube.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;

/*
 * This class is responsible for detection if filename with the same name is already presented in database.
 * If the other file with the same name is already present, than (n) suffix is appended to new filename, where n
 * is number of duplicate.
 */
public class FilenameDuplicationResolver {

	public void changeFilenameIfOneIsAlreadyPresent(List<FileEntity> allUserFiles, FileEntity fileToCheck) {
		UserEntity user = fileToCheck.getUserEntity();

		for (FileEntity tempFile : allUserFiles) {
			if (tempFile.getName().equals(fileToCheck.getName())) {

				String newFilename = getFilenameWithNumberSuffixInParenthesis(fileToCheck
						.getName());
				fileToCheck.setName(newFilename);

				changeFilenameIfOneIsAlreadyPresent(allUserFiles, fileToCheck);
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
}
