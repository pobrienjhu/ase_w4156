package edu.columbia.ase.teamproject.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

@Controller
@RequestMapping("/login.do")
public class LoginController {

	private static final Logger logger =
			LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {
		logger.info("GET /login.do");

		Map<String, Object> model =
				ControllerHelper.createBaseModel(session);
		model.put("title", "Login");

		@SuppressWarnings("unchecked")
		List<NavigationMenuSection> navMenu =
				(List<NavigationMenuSection>) model.get("_menu");
		NavigationMenuSection openIdProviders =
				new NavigationMenuSection("OpenId Providers");
		openIdProviders.addEntry(
				new NavigationMenuEntry(null, "openid_google", "Google"));
		openIdProviders.addEntry(
				new NavigationMenuEntry(null, "openid_yahoo", "Yahoo!"));
		navMenu.add(openIdProviders);

		return new ModelAndView("soy:edu.columbia.ase.login", model);
	}
}
