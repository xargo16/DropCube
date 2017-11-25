package com.dave.dropcube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dave.dropcube.entity.UserFile;
import com.dave.dropcube.service.FileService;

@Controller
public class FileController {

	@Autowired
	private FileService fileService;

	@RequestMapping(value = "/user/addFile")
	public String addFile(@RequestParam("file") MultipartFile multipartFile, Model m){
		if(multipartFile.isEmpty()){
			m.addAttribute("err", "Choose proper file!");
			return "user_dashboard"; //redirect to dashboard and show error
		}
		return "user_dashboard";
	}
}
