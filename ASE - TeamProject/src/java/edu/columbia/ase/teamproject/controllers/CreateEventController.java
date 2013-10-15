package edu.columbia.ase.teamproject.controllers;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.util.GsonProvider;

@Controller
@RequestMapping("/app/createEvent.do")
public final class CreateEventController {

	@Autowired
	EventService eventService;

	@Autowired
	GsonProvider gsonProvider;

	private static final Logger logger = LoggerFactory
			.getLogger(CreateEventController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {
		logger.info("GET /app/createEvent.do");

		Map<String, Object> model = ControllerHelper.createBaseModel(session);

		model.put("title", "Create Event");
		model.put("test", "test");

		return new ModelAndView("soy:edu.columbia.ase.event.createEvent", model);
	}

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		
		// Deseiralize request
		String jsonBody = IOUtils.toString(request.getInputStream());
		logger.info("POST /app/createEvent.do " + jsonBody);

		// Parse request body
		Event event = gsonProvider.provideGson().fromJson(jsonBody, Event.class);

		
		// Get current user
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String username = auth.getName(); // get logged in username

		Event createdEvent = eventService.createEvent(username,
				event.getName(), event.getDescription(), event.getEventType());



		return gsonProvider.provideGson().toJson(createdEvent, Event.class);


	}

}
