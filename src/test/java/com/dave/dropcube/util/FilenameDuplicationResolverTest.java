package com.dave.dropcube.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.dave.dropcube.entity.FileEntity;

public class FilenameDuplicationResolverTest {
	FilenameDuplicationResolver resolver = new FilenameDuplicationResolver();
	
	@Test
	public void When_FileEntityHasGotNameAlreadyPresentInListOfFileNames_Expect_FileEntityNameWillBeChanged(){	
		FileEntity file1 = new FileEntity();
		FileEntity file2 = new FileEntity();
		FileEntity file3 = new FileEntity();
		
		file1.setName("filename X.jpg");
		file2.setName("filename Y.jpg");
		file3.setName("filename Z.jpg");
		
		List<FileEntity> fileNames = new ArrayList<FileEntity>(Arrays.asList(file1, file2, file3));
		
		FileEntity fileToCheck = new FileEntity();
		fileToCheck.setName("filename Y.jpg");
		
		resolver.changeFilenameIfOneIsAlreadyPresent(fileNames, fileToCheck);
		
		assertThat(fileToCheck.getName(), is("filename Y(1).jpg"));
	}
	
	@Test
	public void When_FilenameDoesNotHaveNumberInParenthesesSuffix_Expect_NewFilenameToHaveNumber1InParenthesisSuffix(){
		String filename = "filename.txt";
		String newFilename = resolver.getNewFilenameWithNumberInParenthesisSuffix(filename);
		assertThat(newFilename, is("filename(1).txt"));
	}
	
	@Test
	public void When_FilenameHasNumber3InParenthesesSuffix_Expect_NewFilenameToHaveNumber4InParenthesisSuffix(){
		String filename = "filename(3).txt";
		String newFilename = resolver.getNewFilenameWithNumberInParenthesisSuffix(filename);
		assertThat(newFilename, is("filename(4).txt"));
	}
	
	@Test
	public void When_FilenameDoesNotHaveNumberInParenthesesSuffix_Expect_FilenameHasNumberInParenthesisSuffixReturnFalse(){
		String filename = "My name is Jeff.txt";
		boolean filenameHasNumberInParenthesisSuffix = resolver.filenameHasNumberInParenthesisSuffix(filename);
		assertThat(filenameHasNumberInParenthesisSuffix, is(false));
	}
	
	@Test
	public void When_FilenameHasNumber5InParenthesesSuffix_Expect_FilenameHasNumberInParenthesisSuffixReturnTrue(){
		String filename = "My name is Jeff(5).txt";
		boolean filenameHasNumberInParenthesisSuffix = resolver.filenameHasNumberInParenthesisSuffix(filename);
		assertThat(filenameHasNumberInParenthesisSuffix, is(true));
	}
}
