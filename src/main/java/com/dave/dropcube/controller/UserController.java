package com.dave.dropcube.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.dropcube.entity.User;
import com.dave.dropcube.service.UserService;

/*
 * This controller intercepts all user related 
 * HTTP requests such as register, login etc.
 */
@Controller
public class UserController {
	@Autowired
	UserService userService;

	@RequestMapping(value = { "/", "/index" })
	public String index() {
		return "index";
	}

	@RequestMapping(value = "/register")
	public String registerForm() {
		return "reg-form";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(HttpServletRequest req) {
		User user = new User();
		/*
		 * user.setFirstName(req.getParameter("firstName"));
		 * user.setLastName(req.getParameter("lastName"));
		 * user.setEmail(req.getParameter("email"));
		 * user.setPassword(req.getParameter("password"));
		 * user.setFirstName(req.getParameter("firstName"));
		 */
		user.setFirstName(req.getParameter("firstName"));
		user.setLastName(req.getParameter("lastName"));
		user.setEmail(req.getParameter("email"));
		user.setPassword(req.getParameter("password"));
		user.setPremiumAccount(false);

		return "index";
	}

	@RequestMapping(value = "/about")
	public String about() {

		return "about";
	}
}
