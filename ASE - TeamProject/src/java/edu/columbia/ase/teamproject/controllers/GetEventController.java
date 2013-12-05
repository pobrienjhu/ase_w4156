package edu.columbia.ase.teamproject.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.util.GsonProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class GetEventController.
 */
@Controller
@RequestMapping("/app/getEvent.do")
public class GetEventController {

    /** The event service. */
    @Autowired
    EventService eventService;

    /** The user account dao. */
    @Autowired
    private UserAccountDao userAccountDao;

    /** The gson provider. */
    @Autowired
    GsonProvider gsonProvider;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(GetEventController.class);

    // TODO(pames): we should refactor this so that there are 2
    // variants: one that returns JSON, and one that returns an
    // HTML page that shows the event information. For now we
    // just return the JSON.
    /**
     * Handles HTTP GET requests.
     *
     * @param session
     *            the session
     * @param request
     *            the request
     * @param response
     *            the response
     * @return the string
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String doGet(HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        Gson gson = gsonProvider.provideGson();

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);
        Event event = eventService.lookupEvent(user, Long.valueOf(request.getParameter("eventId")));
        if (event != null) {
            return gson.toJson(event);
        }

        // TODO(pames): return HTTP 404?
        return "{}";
    }

}
