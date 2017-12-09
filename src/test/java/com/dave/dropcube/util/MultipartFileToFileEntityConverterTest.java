package com.dave.dropcube.util;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.FileEntity;

@RunWith(MockitoJUnitRunner.class)
public class MultipartFileToFileEntityConverterTest {
	@Mock
	MultipartFile multipartFile;
	MultipartFileToFileEntityConverter converter = new MultipartFileToFileEntityConverter();
	
	@Test
	public void When_MultipartFileIsPassedToGetFileEntity_Expect_FileEntityWrappsItsDataProperly () throws IOException{
		when(multipartFile.getBytes()).thenReturn("1, 2, 3".getBytes());
		when(multipartFile.getContentType()).thenReturn("text");
		when(multipartFile.getOriginalFilename()).thenReturn("text.txt");
		
		FileEntity file = converter.getFileEntity(multipartFile);
		
		assertThat(file.getContent(), is("1, 2, 3".getBytes()));
		assertThat(file.getContentType(), is("text"));
		assertThat(file.getName(), is("text.txt"));
		
	}
}
