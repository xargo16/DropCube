package com.dave.dropcube.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.dave.dropcube.dao.FileDAO;
import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.exception.ForbiddenFileAccessException;

@RunWith(MockitoJUnitRunner.class)
public class FileServiceImplTest {
	@Mock
	FileDAO fileDAO;
	
	@InjectMocks
	FileServiceImpl fileService = new FileServiceImpl();
	
	@Test
	public void When_FileIsUploadedAndThereIsAnotherFileWithTheSameName_Expect_FilenameWillBeChanged(){
		UserEntity userEntity = new UserEntity();
		FileEntity file1 = new FileEntity();
		FileEntity file2 = new FileEntity();
		FileEntity file3 = new FileEntity();
		FileEntity fileToUpload = new FileEntity();
		
		file1.setName("file 1.txt");
		file1.setUserEntity(userEntity);
		file2.setName("file 2.txt");
		file2.setUserEntity(userEntity);
		file3.setName("file 3.txt");
		file3.setUserEntity(userEntity);
		fileToUpload.setName("file 1.txt");
		fileToUpload.setUserEntity(userEntity);
		
		List<FileEntity> files = new ArrayList<FileEntity>(Arrays.asList(file1, file2, file3));
		when(fileDAO.getAllFiles(any(UserEntity.class))).thenReturn(files);
		
		fileService.uploadFile(fileToUpload);
		
		String nameOfFileAfterUpload = fileToUpload.getName();
		assertThat(nameOfFileAfterUpload, is("file 1(1).txt"));
	}
	
	@Test(expected = ForbiddenFileAccessException.class)
	public void When_UserTriesToGetFileFromAnotherUser_Expect_ForbiddenFileAccessException(){
		FileEntity file = new FileEntity();
		UserEntity userWhoOwnsFile = new UserEntity();
		UserEntity userWhoMakesRequest = new UserEntity();
		
		userWhoOwnsFile.setUserId(1);
		userWhoMakesRequest.setUserId(2);
		file.setUserEntity(userWhoOwnsFile);
		
		when(fileDAO.getFile(1)).thenReturn(file);
		
		fileService.getFile(userWhoMakesRequest, 1);
	}
	
	@Test
	public void When_UserTriesToGetHisFile_Expect_NoException(){
		FileEntity file = new FileEntity();
		UserEntity userWhoOwnsFile = new UserEntity();
		UserEntity userWhoMakesRequest = new UserEntity();
		
		userWhoOwnsFile.setUserId(1);
		userWhoMakesRequest.setUserId(1);
		file.setUserEntity(userWhoOwnsFile);
		
		when(fileDAO.getFile(1)).thenReturn(file);
		
		fileService.getFile(userWhoMakesRequest, 1);
	}
	
	@Test
	public void When_UserTriesToGetMultipleFilesWhichBelongsToHim_Expect_SingleFileEntityInZipFormatIsReturned() throws IOException{
		UserEntity user = new UserEntity();
		FileEntity file1 = new FileEntity();
		FileEntity file2 = new FileEntity();
		FileEntity file3 = new FileEntity();
		
		user.setUserId(1);
	
		file1.setName("file 1.txt");
		file1.setUserEntity(user);
		file1.setContent("1".getBytes());
		
		file2.setName("file 2.txt");
		file2.setUserEntity(user);
		file2.setContent("1".getBytes());
		
		file3.setName("file 3.txt");
		file3.setUserEntity(user);
		file3.setContent("1".getBytes());
		
		List<FileEntity> files = new ArrayList<FileEntity>(Arrays.asList(file1, file2, file3));
		when(fileDAO.getMultipleFiles(new int[]{1, 2, 3})).thenReturn(files);
		
		FileEntity zippedFile = fileService.getMultipleFiles(user, new int[] {1, 2, 3});
		
		assertThat(zippedFile.getName(), is("DropCube.zip"));
	}
	
	@Test(expected = ForbiddenFileAccessException.class)
	public void When_UserTriesToDeleteFileFromAnotherUser_Expect_ForbiddenFileAccessException(){
		FileEntity file = new FileEntity();
		UserEntity userWhoOwnsFile = new UserEntity();
		UserEntity userWhoMakesRequest = new UserEntity();
		
		userWhoOwnsFile.setUserId(1);
		userWhoMakesRequest.setUserId(2);
		file.setUserEntity(userWhoOwnsFile);
		
		when(fileDAO.getFile(1)).thenReturn(file);
		
		fileService.deleteFile(userWhoMakesRequest, 1);
	}
	
	@Test
	public void When_UserTriesToDeleteHisFile_Expect_NoException(){
		FileEntity file = new FileEntity();
		UserEntity userWhoOwnsFile = new UserEntity();
		UserEntity userWhoMakesRequest = new UserEntity();
		
		userWhoOwnsFile.setUserId(1);
		userWhoMakesRequest.setUserId(1);
		file.setUserEntity(userWhoOwnsFile);
		
		when(fileDAO.getFile(1)).thenReturn(file);
		
		fileService.deleteFile(userWhoMakesRequest, 1);
	}
	
	@Test
	public void When_3IdsArePresentInUrlResource_Expect_ArrayWith3ElementsIsReturned(){
		String urlResource = "1,22,31";
		int[] ids = fileService.convertIdsFromUrlResourceToIntArray(urlResource);
		Integer[] idsAsObject = new Integer[ids.length];
		for(int i = 0; i < ids.length; i++)
			idsAsObject[i] = ids[i];

		assertThat(idsAsObject, hasItemInArray(1));
		assertThat(idsAsObject, hasItemInArray(22));
		assertThat(idsAsObject, hasItemInArray(31));
		assertThat(idsAsObject, arrayWithSize(3));
	}
	
	@Test
	public void When_3IdsArePresentInUrlResource_Expect_UrlResourcePointsToMultipleFilesReturnsTrue(){
		String urlResource = "1, 22, 31";
		boolean isUrlResourcePointingToMultipleFiles = fileService.urlResourcePointsToMultipleFiles(urlResource);
		assertThat(isUrlResourcePointingToMultipleFiles, is(true));
	}
	@Test
	public void When_1IdIsPresentInUrlResource_Expect_UrlResourcePointsToMultipleFilesReturnsFalse(){
		String urlResource = "1";
		boolean isUrlResourcePointingToMultipleFiles = fileService.urlResourcePointsToMultipleFiles(urlResource);
		assertThat(isUrlResourcePointingToMultipleFiles, is(false));
	}
}
