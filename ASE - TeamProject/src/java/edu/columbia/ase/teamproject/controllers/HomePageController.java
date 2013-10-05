package edu.columbia.ase.teamproject.controllers;

import javax.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/index.do")
@PermitAll
public class HomePageController {

	private static final Logger logger =
			LoggerFactory.getLogger(HomePageController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String doGet() {
		logger.info("GET /index.do");
		return "soy:edu.columbia.ase.index";
	}

}
