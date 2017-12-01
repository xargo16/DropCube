package com.dave.dropcube.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.service.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/user/addFile")
	public String uploadFile(
			@RequestParam("file") MultipartFile[] multipartFileArray, Model m) {
		if (multipartFileArray.length == 0) {
			m.addAttribute("err", "Choose proper file!");
			return "user_dashboard"; // redirect to dashboard and show error
		}

		try {
			for (MultipartFile multipartFile : multipartFileArray) {
				FileEntity file = new FileEntity();
				file.setContent(multipartFile.getBytes());
				file.setDateOfUpload(new Date());
				file.setName(multipartFile.getOriginalFilename());
				file.setContentType(multipartFile.getContentType());
				fileService.uploadFile(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/user?err=upload";
		}

		return "redirect:/user?act=success";
	}

	@RequestMapping(value = "/user/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadFiles(@PathVariable("fileId") String urlFileId,
			HttpServletResponse response) throws IOException {
		FileEntity file = null;

		if (urlPathVariablePointsToMultipleFiles(urlFileId)) {
			int[] fileIds = convertIdsFromUrlPathVariableToIntArray(urlFileId);
			file = fileService.getMultipleFiles(fileIds); //Zip file wrapped in FileEntity
			
		} else {
			file = fileService.getFile(Integer.parseInt(urlFileId));
		}

		byte[] requestedFilesBytes = file.getContent();

		response.setContentType("application/force-download");
		response.setHeader("Content-disposition", "attachment; filename="
				+ file.getName());
		response.getOutputStream().write(requestedFilesBytes);
	}

	@RequestMapping(value = "/user/delete/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public String deleteFiles(@PathVariable("fileId") String urlFileId, Model model) {
		if (urlPathVariablePointsToMultipleFiles(urlFileId)) {
			int[] fileIds = convertIdsFromUrlPathVariableToIntArray(urlFileId);
			fileService.deleteMultipleFiles(fileIds);
			model.addAttribute("delete", fileIds.length + " files deleted");
		} else {
			fileService.deleteFile(Integer.parseInt(urlFileId));
			model.addAttribute("delete", "File deleted");
		}
		
		return "redirect:/user";
	}
	
	private int[] convertIdsFromUrlPathVariableToIntArray(String url){
		String[] stringFileIds = url.split(",");
		int[] intFileIds = new int[stringFileIds.length];
		
		for (int i = 0; i < stringFileIds.length; i++) {
			intFileIds[i] = Integer.parseInt(stringFileIds[i]);
		}
		return intFileIds;
	}
	private boolean urlPathVariablePointsToMultipleFiles(String urlPathVariable){
		String regexForDetectingMultipleFilesDownloadRequest = "\\d+,.+";
		return urlPathVariable.matches(regexForDetectingMultipleFilesDownloadRequest);
	}
}
