package com.dave.dropcube.util;

import java.io.IOException;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.FileEntity;

public class MultipartFileToFileEntityConverter {
	public FileEntity getFileEntity(MultipartFile multipartFile)
			throws IOException {
		FileEntity file = new FileEntity();
		file.setContent(multipartFile.getBytes());
		file.setDateOfUpload(new Date());
		file.setName(multipartFile.getOriginalFilename());
		file.setContentType(multipartFile.getContentType());
		
		return file;
	}
}
