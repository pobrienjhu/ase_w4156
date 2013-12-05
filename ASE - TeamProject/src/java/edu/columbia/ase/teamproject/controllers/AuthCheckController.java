package edu.columbia.ase.teamproject.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

// TODO: Auto-generated Javadoc
/**
 * The Class AuthCheckController.
 */
@Controller
@RequestMapping("/app/authCheck.do")
public class AuthCheckController {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AuthCheckController.class);

    /**
     * Handles HTTP GET requests.
     *
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String doGet() {
        logger.info("GET /app/authCheck.do");
        return "OK";
    }
}
