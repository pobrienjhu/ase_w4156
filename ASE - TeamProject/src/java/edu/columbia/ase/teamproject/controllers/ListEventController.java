package edu.columbia.ase.teamproject.controllers;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.services.EventService;

@Controller
@RequestMapping("/app/listEvent.do")
public class ListEventController {
	
	@Autowired
	EventService eventService;

	@Autowired
	private UserAccountDao userAccountDao;

	private static final Logger logger = LoggerFactory
			.getLogger(ListEventController.class);

	private List<Map<String, Object>> buildEventDataList(Collection<Event> events) {
		List<Map<String, Object>> rv = Lists.newArrayList();

		for (Event e: events) {
			Map<String, Object> eventData = Maps.newHashMap();
			eventData.put("id", e.getId().toString());
			eventData.put("name", e.getName());
			eventData.put("description", e.getDescription());
			rv.add(eventData);
		}
		return rv;
	}

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);

		Map<String, Object> model =
				ControllerHelper.createBaseModel(session);

		String displayName = StringUtils.defaultIfEmpty(user.getDisplayName(), user.getEmail() );
		
		model.put("userName", displayName );
		model.put("title", "Event List");

		Collection<Event> currentPublicEvents =
				eventService.getAllActivePublicEvents(DateTime.now());
		Collection<Event> currentPrivateEvents =
				eventService.getAllActivePrivateEventsForUserId(DateTime.now(),
						user.getId());

		model.put("publicEventData", buildEventDataList(currentPublicEvents));
		model.put("privateEventData", buildEventDataList(currentPrivateEvents));

		return new ModelAndView("soy:edu.columbia.ase.event.listEvent", model);
		
	}

}
