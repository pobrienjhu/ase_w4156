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

import com.google.common.collect.Maps;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.security.AuthKey;

@Controller
@RequestMapping("/app/mobileSetup.do")
public class MobileSetupController {

	@Autowired
	UserAccountDao userAccountDao;

	private static final Logger logger =
			LoggerFactory.getLogger(MobileSetupController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(HttpSession session) {
		logger.info("GET /app/mobileSetup.do");
		Map<String, Object> model =
				ControllerHelper.createBaseModel(session);

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);

		// TODO(pames): don't hard code the secret.
		AuthKey apiKey = AuthKey.authKeyForIdAndSecret(user.getId(), "secret");
		model.put("apiKey", apiKey.getApiKey());

		model.put("title", "Mobile App Setup");

		return new ModelAndView("soy:edu.columbia.ase.mobile", model);
	}

}