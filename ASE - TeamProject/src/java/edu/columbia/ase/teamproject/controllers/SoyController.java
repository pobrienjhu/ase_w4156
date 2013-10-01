package edu.columbia.ase.teamproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/soy")
public class SoyController {

	@RequestMapping(method = RequestMethod.GET)
	public String handleRequest() {
		return "edu.columbia.ase.index";
	}
}
