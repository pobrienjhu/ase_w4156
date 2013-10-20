package edu.columbia.ase.teamproject.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.util.GsonProvider;
import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

@Controller
@RequestMapping("/app/voteEvent.do")
public final class VoteController {

	@Autowired
	EventService eventService;

	@Autowired
	GsonProvider gsonProvider;
	
	@Autowired
	private UserAccountDao userAccountDao;

	private static final Logger logger = LoggerFactory
			.getLogger(CreateEventController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(HttpSession session, HttpServletRequest request) {
		logger.info("GET /app/voteEvent.do");

		Map<String, Object> model = ControllerHelper.createBaseModel(session);

		model.put("title", "Event Voting");
		model.put("test", "test");
		

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);
	
		Event event = eventService.lookupEvent(user,
				Long.valueOf(request.getParameter("event")));
		
		@SuppressWarnings("unchecked")
		List<NavigationMenuSection> nms =
				(List<NavigationMenuSection>) model.get("_vote");
		
		for(VoteCategory v : event.getVoteCategories() ){

			NavigationMenuSection voteCatSection =
					new NavigationMenuSection(v.getDescription());
			for(VoteOption vo : v.getVoteOptions()){
				voteCatSection.addEntry(
						new NavigationMenuEntry(v.getCategoryName(), vo.getId().toString(),vo.getOptionName()));
			}
			nms.add(voteCatSection);
		}

		return new ModelAndView("soy:edu.columbia.ase.vote.voteEvent", model);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView doPost(HttpSession session, HttpServletRequest request, HttpServletResponse response)
	{
		logger.info("POST /app/voteEvent.do");
		
		Map<String, Object> model = ControllerHelper.createBaseModel(session);
		model.put("title", "Home");
		model.put("test", "test");
		return new ModelAndView("soy:edu.columbia.ase.event.createEvent", model);


	}



}
