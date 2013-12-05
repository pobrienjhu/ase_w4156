package edu.columbia.ase.teamproject.controllers;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.util.GsonProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class GetEventsPublicController.
 */
@Controller
@RequestMapping("/app/getEventsPublic.do")
public class GetEventsPublicController {
	
	/** The event service. */
	@Autowired
	EventService eventService;

	/** The gson provider. */
	@Autowired
	GsonProvider gsonProvider;

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(GetEventsPublicController.class);

	/**
	 * Handles HTTP GET requests.
	 *
	 * @param session the session
	 * @param request the request
	 * @param response the response
	 * @return the string
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public String doGet(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		
		try {
			Gson gson = gsonProvider.provideGson();
			
			String eventType = StringUtils.defaultIfEmpty(request.getParameter("eventType"), "active") ;
			
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			
			Collection<Event> events = new ArrayList<Event>();
			
			if( StringUtils.equalsIgnoreCase(eventType, "active") ){
				events = eventService.getAllActivePublicEvents(new DateTime());
			} else if ( StringUtils.equalsIgnoreCase(eventType, "completed") ) {
				events = eventService.getAllCompletedPublicEvents(new DateTime());
			}
					
			
			if (events != null) {
				return gson.toJson(events);
			}
		} catch (Exception e) {
			logger.error("Unable to find public event list. Root Cause ("+e.getMessage()+")");
		}

		return "{}";
	}

}
