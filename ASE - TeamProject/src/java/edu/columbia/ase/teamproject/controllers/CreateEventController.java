package edu.columbia.ase.teamproject.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/app/createEvent.do")
public final class CreateEventController {

	private static final Logger logger =
			LoggerFactory.getLogger(CreateEventController.class);

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {
		logger.info("GET /app/createEvent.do");

		Map<String, Object> model =
				ControllerHelper.createBaseModel(session);

		model.put("title", "Create Event");

		return new ModelAndView("soy:edu.columbia.ase.event.createEvent", model);
	}

	
}
