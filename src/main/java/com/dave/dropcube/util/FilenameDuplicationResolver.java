package com.dave.dropcube.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dave.dropcube.entity.FileEntity;

/*
 * This class is responsible for detection if filename with the same name is already present in database.
 * If the other file with the same name is already present, than (n) suffix is appended to new filename, where n
 * is number of duplicate.
 */
public class FilenameDuplicationResolver {

	public void changeFilenameIfOneIsAlreadyPresent(
			List<FileEntity> allUserFiles, FileEntity fileToCheck) {
		for (FileEntity tempFile : allUserFiles) {
			if (tempFile.getName().equals(fileToCheck.getName())) {

				String filename = getNewFilenameWithNumberInParenthesisSuffix(fileToCheck.getName());
				fileToCheck.setName(filename);

				// Recursion to check if new name is not already present in DB
				// as well
				changeFilenameIfOneIsAlreadyPresent(allUserFiles, fileToCheck);
			}
		}
	}

	String getNewFilenameWithNumberInParenthesisSuffix(String currentFilename) {
		String newFilename = null;
		if (filenameHasNumberInParenthesisSuffix(currentFilename)) {
			newFilename = getNewFilenameWithIncrementedNumberInParenthesisSuffix(currentFilename);
		} else {
			newFilename = getNewFilenameWithNumberOneInParenthesisSuffix(currentFilename);
		}

		return newFilename;
	}

	boolean filenameHasNumberInParenthesisSuffix(String filename) {
		String regexForMatchingFilenameWithNumberInParenthesisSuffix = "(.+)(\\((\\d+)\\))\\..+";
		return filename.matches(regexForMatchingFilenameWithNumberInParenthesisSuffix);
	}

	String getNewFilenameWithIncrementedNumberInParenthesisSuffix(String filename) {
		String newFilename = null;
		String regexForGroupingFilenameWithNumberInParenthesisSuffixAndExtension = "(.+)(\\((\\d+)\\))(\\..+)";
		Pattern pattern = Pattern
				.compile(regexForGroupingFilenameWithNumberInParenthesisSuffixAndExtension);
		Matcher matcher = pattern.matcher(filename);

		// Group 1 - filename, Group 2 - parenthesis, Group 3 - number in
		// parenthesis, Group 4 - extension
		if (matcher.find()) {
			String numberInParenthesis = matcher.group(3);
			String newNumberInParenthesis = ""
					+ (Integer.parseInt(numberInParenthesis) + 1);

			newFilename = matcher.group(1) + "(" + newNumberInParenthesis + ")"
					+ matcher.group(4);

		}
		return newFilename;
	}

	String getNewFilenameWithNumberOneInParenthesisSuffix(String filename) {
		String newFilename = null;
		String regexForGroupingFilenameAndExtension = "(.+)(\\..+)";
		Pattern pattern = Pattern.compile(regexForGroupingFilenameAndExtension);
		Matcher matcher = pattern.matcher(filename);
		// Group 1 - filename, Group 2 - extension
		if (matcher.find()) {
			newFilename = matcher.group(1) + "(1)" + matcher.group(2);
		}
		return newFilename;
	}
}
