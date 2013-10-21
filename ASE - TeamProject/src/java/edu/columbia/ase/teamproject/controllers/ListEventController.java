package edu.columbia.ase.teamproject.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.util.GsonProvider;

@Controller
@RequestMapping("/app/listEvent.do")
public class ListEventController {
	
	@Autowired
	EventService eventService;

	@Autowired
	private UserAccountDao userAccountDao;

	@Autowired
	GsonProvider gsonProvider;

	private static final Logger logger = LoggerFactory
			.getLogger(ListEventController.class);

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {
		
		Gson gson = gsonProvider.provideGson();

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);


		Map<String, Object> model =
				ControllerHelper.createBaseModel(session);

		model.put("title", "Administration");
		model.put("admin", true);

		return new ModelAndView("soy:edu.columbia.ase.event.listEvent", model);
		
	}

}
