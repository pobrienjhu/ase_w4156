package edu.columbia.ase.teamproject.controllers;

import java.util.Map;

import javax.annotation.security.PermitAll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;

@Controller
@RequestMapping("/index.do")
@PermitAll
public class HomePageController {

	private static final Logger logger =
			LoggerFactory.getLogger(HomePageController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet() {
		logger.info("GET /index.do");

		Map<String, String> model = Maps.newHashMap();
		model.put("title", "Home");

		return new ModelAndView("soy:edu.columbia.ase.index", model);
	}

}