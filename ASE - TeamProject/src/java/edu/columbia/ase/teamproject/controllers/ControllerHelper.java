package edu.columbia.ase.teamproject.controllers;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;

import com.google.common.collect.Maps;

public class ControllerHelper {

	private static final GrantedAuthority ADMIN_AUTHORITY =
			AuthorityUtils.createAuthorityList("ROLE_ADMIN").get(0);

	/** 
	 * @param session the current HttpSession
	 * @return a map containing values for the keys "_csrf" and "_admin" to be
	 *     used by common template code.
	 */
	public static Map<String, Object> createBaseModel(HttpSession session) {
		Map<String, Object> model = Maps.newHashMap();

		CsrfToken token = (CsrfToken) session.getAttribute("_csrf");
		if (token != null) {
			model.put("_csrf", token.getToken());
		}

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication != null &&
			authentication.getAuthorities().contains(ADMIN_AUTHORITY)) {
			model.put("_admin", true);
		}

		return model;
	}

}