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

// TODO: Auto-generated Javadoc
/**
 * The Class LoginController.
 */
@Controller
@RequestMapping("/login.do")
public class LoginController {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * Handles HTTP GET requests.
     *
     * @param session
     *            the session
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(final HttpSession session) {
        logger.info("GET /login.do");

        final Map<String, Object> model = ControllerHelper.createBaseModel(session);
        model.put("title", "Login");

        @SuppressWarnings("unchecked")
        final List<NavigationMenuSection> navMenu = (List<NavigationMenuSection>) model
                .get("_menu");
        final NavigationMenuSection openIdProviders = new NavigationMenuSection("OpenId Providers");
        openIdProviders.addEntry(new NavigationMenuEntry(null, "openid_google", "Google"));
        openIdProviders.addEntry(new NavigationMenuEntry(null, "openid_yahoo", "Yahoo!"));
        navMenu.add(openIdProviders);

        return new ModelAndView("soy:edu.columbia.ase.login", model);
    }
}
