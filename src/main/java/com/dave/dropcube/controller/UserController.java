package com.dave.dropcube.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@RequestMapping(value={"/", "/index"})
	public String index(){
		System.out.println("HA");
		return "index";
	}
	
	@RequestMapping(value="/login")
	public String login(){

		return "login";
	}

}
