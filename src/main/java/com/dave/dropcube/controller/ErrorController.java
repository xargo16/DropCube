package com.dave.dropcube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
	@RequestMapping(value = "/404")
	public String pageNotFound() {
		return "redirect:/";
	}

	@RequestMapping(value = { "/500", "/400" })
	public String internalServerError() {
		return "error";
	}

	@RequestMapping(value = "/cookies")
	public String cookiesDisabled() {
		return "cookies";
	}

	@RequestMapping(value = "/js")
	public String javaScriptDisabled() {
		return "js";
	}
}
