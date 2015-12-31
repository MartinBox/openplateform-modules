package com.open.cas.shiro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "cas2")
public class CasController {

	@RequestMapping(value = "")
	public String index(Model model) {
		return "index";
	}
}
