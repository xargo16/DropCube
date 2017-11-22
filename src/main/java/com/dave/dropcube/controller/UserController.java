package com.dave.dropcube.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.dropcube.command.LoginCommand;
import com.dave.dropcube.command.UserCommand;
import com.dave.dropcube.entity.User;
import com.dave.dropcube.exception.InvalidRegistrationDataException;
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

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute LoginCommand loginCommand, Model model) {
		String email = loginCommand.getEmail();
		String password = loginCommand.getPassword();
		User user = userService.login(email, password);
		
		if(user == null){
			model.addAttribute("err", "Invalid email or password");
			return "index";
		}
		else{
			return "index";
		}
	}

	// TODO
	// Check if every value is correct
	@RequestMapping(value = "/register")
	public String registerForm() {
		return "reg-form";
	}

	/*
	 * This method handles registration process.
	 * InvalidRegistrationDataException is thrown when user provides invalid
	 * data(email = 123 etc.) 
	 * DataIntegrityViolationException is thrown when
	 * email is already presented in database(User already exists)
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String register(@ModelAttribute UserCommand userCommand, Model model) {
		try {
			userService.register(userCommand.getUser());

		} catch (InvalidRegistrationDataException ex) {
			model.addAttribute("err", ex.getMessage());
			return "reg-form";

		} catch (DataIntegrityViolationException ex) {
			model.addAttribute("err",
					"This email is already registered! Try different one.");
			return "reg-form";
		}

		return "redirect:index?act=reg";
	}

	@RequestMapping(value = "/about")
	public String about() {

		return "about";
	}
}
