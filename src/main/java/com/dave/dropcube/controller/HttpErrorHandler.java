package com.dave.dropcube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HttpErrorHandler {
	@RequestMapping(value = "/404")
	public String pageNotFound(){
		return "redirect:/";
	}
	
	@RequestMapping(value = "/500")
	public String internalServerError(){
		return "error";
	}
}
