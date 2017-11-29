package com.dave.dropcube.service;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.dave.dropcube.entity.UserFile;

@Service
public class FileServiceImpl implements FileService{

	public void saveFile(UserFile file) {
		byte[] bytes = file.getContent();
		try {
			BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("/DropCube/file.txt"));
			out.write(bytes);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public UserFile getFile(int fileId) {
		return null;
	}

	public UserFile[] getAllFiles(int userId) {
		UserFile[] user = new UserFile[5];
		
		for(int i = 0; i < user.length; i++){
			user[i] = new UserFile();
			String dupa = "DUPA";
			user[i].setContent(dupa.getBytes());
			user[i].setDateOfUpload(new Date());
			user[i].setName("DUPA " + i + ".txt");
			user[i].setFileId(i);
		}
		return user;
	}

	public void deleteFile(int fileId) {
	}

	public void deleteAllFiles(int userId) {
	}

}
