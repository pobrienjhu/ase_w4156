package edu.columbia.ase.teamproject.controllers;

import java.util.Map;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

// TODO: Auto-generated Javadoc
/**
 * The Class HomePageController.
 */
@Controller
@RequestMapping("/index.do")
@PermitAll
public class HomePageController {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(HomePageController.class);

    /**
     * Handles HTTP GET requests.
     *
     * @param session
     *            the session
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpSession session) {
        logger.info("GET /index.do");

        Map<String, Object> model = ControllerHelper.createBaseModel(session);
        model.put("title", "Home");

        return new ModelAndView("soy:edu.columbia.ase.index", model);
    }

}
