package com.dave.dropcube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		System.out.println("HA");
		return "index";
	}

	@RequestMapping(value = "/register")
	public String register() {

		return "reg-form";
	}

	@RequestMapping(value = "/about")
	public String about() {

		return "about";
	}
}
