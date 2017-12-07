package com.dave.dropcube.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.dave.dropcube.entity.FileEntity;

/*
 * This class is responsible for converting multiple FileEntity objects
 * to one FileEntity which content is zip file
 */
public class MultipleFileEntitiesToZipConverter {
	public FileEntity packMultipleFileEntityObjectsInOneFileEntity(
			List<FileEntity> files) throws IOException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
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
}
