package com.dave.dropcube.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.service.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/user/addFile")
	public String uploadFile(
			@RequestParam("file") MultipartFile[] multipartFileArray,
			HttpSession session, Model m) {
		if (isUserLoggedIn(session)) {
			if (multipartFileArray.length == 0) {
				m.addAttribute("err", "Choose proper file!");
				return "user_dashboard"; // redirect to dashboard and show error
			}

			try {
				for (MultipartFile multipartFile : multipartFileArray) {
					FileEntity file = new FileEntity();
					UserEntity user = (UserEntity) session.getAttribute("user");

					file.setContent(multipartFile.getBytes());
					file.setDateOfUpload(new Date());
					file.setName(multipartFile.getOriginalFilename());
					file.setContentType(multipartFile.getContentType());
					file.setUserEntity(user);
					fileService.uploadFile(file);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return "redirect:/user?err=upload";
			}

			return "redirect:/user?act=success";
		}
		return "redirect:/index";
	}
	
	@RequestMapping(value="/user/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFileContent(@PathVariable("fileId") String urlFileId, HttpSession session, HttpServletResponse response) throws IOException{
		UserEntity user = (UserEntity)session.getAttribute("user");
		FileEntity file = getFileBasedOnUrlFileId(user, urlFileId);

		byte[] requestedFilesBytes = file.getContent();
		
		response.getOutputStream().write(requestedFilesBytes);
	    response.getOutputStream().flush();
	    
	}

	@RequestMapping(value = "/user/download/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadFiles(@PathVariable("fileId") String urlFileId,
			HttpSession session, HttpServletResponse response) throws IOException {
		UserEntity user = (UserEntity)session.getAttribute("user");
		FileEntity file = getFileBasedOnUrlFileId(user, urlFileId);

		byte[] requestedFilesBytes = file.getContent();

		response.setContentType("application/force-download");
		response.setHeader("Content-disposition", "attachment; filename="
				+ file.getName());
		response.getOutputStream().write(requestedFilesBytes);
	}
	private FileEntity getFileBasedOnUrlFileId(UserEntity user, String urlFileId) throws IOException{
		if (urlPathVariablePointsToMultipleFiles(urlFileId)) {
			int[] fileIds = convertIdsFromUrlPathVariableToIntArray(urlFileId);
			return fileService.getMultipleFiles(user, fileIds); // Zip file wrapped in
															// FileEntity

		} else {
			return fileService.getFile(user, Integer.parseInt(urlFileId));
		}
	}
	
	@RequestMapping(value = "/user/delete/{fileId}")
	public String deleteFiles(@PathVariable("fileId") String urlFileId, HttpSession session,
			Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");
		
		if (urlPathVariablePointsToMultipleFiles(urlFileId)) {
			int[] fileIds = convertIdsFromUrlPathVariableToIntArray(urlFileId);
			fileService.deleteMultipleFiles(user, fileIds);
			model.addAttribute("delete", fileIds.length + " files deleted");
		} else {
			fileService.deleteFile(user, Integer.parseInt(urlFileId));
			model.addAttribute("delete", "File deleted");
		}

		return "redirect:/user";
	}

	private boolean urlPathVariablePointsToMultipleFiles(String urlPathVariable) {
		String regexForDetectingMultipleFilesDownloadRequest = "\\d+,.+";
		return urlPathVariable
				.matches(regexForDetectingMultipleFilesDownloadRequest);
	}

	private int[] convertIdsFromUrlPathVariableToIntArray(String url) {
		String[] stringFileIds = url.split(",");
		int[] intFileIds = new int[stringFileIds.length];

		for (int i = 0; i < stringFileIds.length; i++) {
			intFileIds[i] = Integer.parseInt(stringFileIds[i]);
		}
		return intFileIds;
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("user") != null)
			return true;
		return false;
	}
}
