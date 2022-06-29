package com.triple.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
	
	@RequestMapping("/jsp")
	public String loginView() throws Exception{
		return "main";
	}
}
