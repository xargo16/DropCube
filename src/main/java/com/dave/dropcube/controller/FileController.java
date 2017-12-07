package com.dave.dropcube.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
import com.dave.dropcube.service.FileService;
import com.dave.dropcube.util.MultipartFileToFileEntityConverter;

/*
 * This controller intercepts all file related 
 * HTTP requests such as upload, download and delete
 */
@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/user/upload", method = RequestMethod.POST)
	public String uploadFile(
			@RequestParam("file") MultipartFile[] multipartFileArray,
			HttpSession session, Model model) {
		if (isUserLoggedIn(session)) {
			if (multipartFileArray.length == 0) {
				model.addAttribute("err", "Choose proper file!");
				return "user_dashboard";
			}

			try {
				MultipartFileToFileEntityConverter converter = new MultipartFileToFileEntityConverter();
				for (MultipartFile multipartFile : multipartFileArray) {
					UserEntity user = (UserEntity) session.getAttribute("user");
					FileEntity file = converter.getFileEntity(multipartFile);
					file.setUserEntity(user);

					fileService.uploadFile(file);
				}
			} catch (Exception e) {
				return "redirect:/user?err=upload";
			}

			return "redirect:/user?act=success";
		}
		return "redirect:/index";
	}

	@RequestMapping(value = "/user/download/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void downloadFiles(@PathVariable("fileId") String urlResource,
			HttpSession session, HttpServletResponse response)
			throws IOException {
		UserEntity user = (UserEntity) session.getAttribute("user");
		FileEntity file = fileService.getFileBasedOnUrlResource(user,
				urlResource);

		byte[] requestedFilesBytes = file.getContent();

		response.setContentType("application/force-download");
		response.setHeader("Content-disposition", "attachment; filename="
				+ file.getName());
		response.getOutputStream().write(requestedFilesBytes);
	}

	@RequestMapping(value = "/user/{fileId}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	public void getFileContent(@PathVariable("fileId") String urlResource,
			HttpSession session, HttpServletResponse response)
			throws IOException {
		UserEntity user = (UserEntity) session.getAttribute("user");
		FileEntity file = fileService.getFileBasedOnUrlResource(user,
				urlResource);

		byte[] requestedFilesBytes = file.getContent();

		response.getOutputStream().write(requestedFilesBytes);
		response.getOutputStream().flush();

	}

	@RequestMapping(value = "/user/delete/{fileId}")
	public String deleteFiles(@PathVariable("fileId") String urlResource,
			HttpSession session, Model model) {
		UserEntity user = (UserEntity) session.getAttribute("user");

		fileService.deleteFileBasedOnUrlResource(user, urlResource);
		model.addAttribute("delete", "Deletion successful");

		return "redirect:/user";
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("user") != null)
			return true;
		return false;
	}
}
