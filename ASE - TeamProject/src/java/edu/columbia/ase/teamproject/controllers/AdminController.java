package edu.columbia.ase.teamproject.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.security.Permission;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminController.
 */
@Controller
@RequestMapping("/admin/index.do")
public class AdminController {

    /** The user dao. */
    @Autowired
    UserAccountDao userDao;

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    /** The Constant VALID_ACTIONS. */
    private static final List<String> VALID_ACTIONS = ImmutableList.<String> of("grant", "revoke");

    /**
     * Handles HTTP GET requests.
     *
     * @param session
     *            the session
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView doGet(HttpSession session) {
        logger.info("GET /admin/index.do");

        Map<String, Object> model = ControllerHelper.createBaseModel(session);

        model.put("title", "Administration");
        model.put("admin", true);

        return new ModelAndView("soy:edu.columbia.ase.admin", model);
    }

    /**
     * Handles HTTP POST requests
     *
     * @param session
     *            the session
     * @param action
     *            the action
     * @param email
     *            the email
     * @return the model and view
     */
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView doPost(HttpSession session, @RequestParam String action,
            @RequestParam String email) {
        Preconditions.checkArgument(VALID_ACTIONS.contains(action));
        Preconditions.checkArgument(!email.isEmpty());

        Map<String, Object> model = ControllerHelper.createBaseModel(session);
        model.put("title", "Administration");
        model.put("admin", true);

        UserAccount user = userDao.findAccountByEmail(email);
        if (user != null) {
            if (action.equals("grant")) {
                if (user.getPermissions().contains(Permission.ADMIN)) {
                    model.put("message", "User already has admin privileges.");
                } else {
                    model.put("message", "Permission granted!  Please have the user log out "
                            + "/ back in for the change to take effect.");
                    user.addPermission(Permission.ADMIN);
                }
            } else {
                if (!user.getPermissions().contains(Permission.ADMIN)) {
                    model.put("message", "User does not have admin privileges.");
                } else {
                    model.put("message", "Permission revoked!  Please have the user log out "
                            + "/ back in for the change to take effect.");
                    user.revokePermission(Permission.ADMIN);
                }
            }
            userDao.add(user);
        } else {
            model.put("message", "No account found with that username.");
        }

        return new ModelAndView("soy:edu.columbia.ase.admin", model);
    }
}
