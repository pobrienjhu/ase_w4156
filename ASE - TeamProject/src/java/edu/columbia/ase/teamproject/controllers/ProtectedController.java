package edu.columbia.ase.teamproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/protected.do")
public class ProtectedController {

	private static final Logger logger =
			LoggerFactory.getLogger(ProtectedController.class);

	@RequestMapping(method = RequestMethod.GET)
	public String doGet() {
		logger.info("GET /protected.do");
		return "soy:edu.columbia.ase.protected";
	}

}
