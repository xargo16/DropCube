package com.dave.dropcube.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.dave.dropcube.command.LoginCommand;
import com.dave.dropcube.command.UserCommand;
import com.dave.dropcube.entity.FileEntity;
import com.dave.dropcube.entity.UserEntity;
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
	public String index(HttpSession session) {
		if (isUserLoggedIn(session)) {
			return "redirect:/user";
		}

		return "index";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute LoginCommand loginCommand, Model model,
			HttpSession session) throws IOException {
		UserEntity user = userService.login(loginCommand.getEmail(),
				loginCommand.getPassword());

		if (user == null) {
			model.addAttribute("err", "Invalid email or password");
			return "index";
		} else {
			session.setAttribute("user", user);
			return "redirect:/user";
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index?act=lout";
	}

	@RequestMapping("/register")
	public String registerForm(HttpSession session) {
		if (isUserLoggedIn(session))
			return "redirect:/user";
		return "reg-form";
	}

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

		return "redirect:/index?act=reg";
	}

	@RequestMapping("/user")
	public String userDashboard(HttpSession session, Model model) {
		if (!isUserLoggedIn(session))
			return "redirect:/index";

		UserEntity user = (UserEntity) session.getAttribute("user");
		List<FileEntity> userFiles = userService.getUserFiles(user);

		if (userFiles.size() > 0)
			model.addAttribute("files", userFiles);
		return "user_dashboard";
	}

	private boolean isUserLoggedIn(HttpSession session) {
		if (session.getAttribute("user") != null)
			return true;
		return false;
	}

}
