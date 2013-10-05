package edu.columbia.ase.teamproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/login.do")
public class LoginController {

	@RequestMapping(method = RequestMethod.GET)
	public String doGet() {
		return "soy:edu.columbia.ase.login";
	}
}
