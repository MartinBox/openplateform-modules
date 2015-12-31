package com.open.cas.shiro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.open.cas.shiro.entity.User;
import com.open.cas.shiro.service.UserService;

@Controller
@RequestMapping(value = "user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "index")
	public String index(Model model) {
		User user = userService.findById(1L);
		model.addAttribute("user", user);
		return "index";
	}

	@RequestMapping(value = "logout")
	public String logout(Model model) {
		System.out.println("logout");
		return "login";
	}
}
