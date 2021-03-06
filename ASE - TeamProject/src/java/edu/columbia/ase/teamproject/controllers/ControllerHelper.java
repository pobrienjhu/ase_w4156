package edu.columbia.ase.teamproject.controllers;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;

import com.google.common.collect.Maps;

import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

// TODO: Auto-generated Javadoc
/**
 * The Class ControllerHelper.
 */
public class ControllerHelper {

    /** The Constant ADMIN_AUTHORITY. */
    private static final GrantedAuthority ADMIN_AUTHORITY = AuthorityUtils.createAuthorityList(
            Permission.ADMIN.toString()).get(0);

    /** The Constant ANONYMOUS_AUTHORITY. */
    private static final GrantedAuthority ANONYMOUS_AUTHORITY = AuthorityUtils.createAuthorityList(
            "ROLE_ANONYMOUS").get(0);

    /**
     * Creates the base model.
     *
     * @param session
     *            the current HttpSession
     * @return a map containing values for the keys "_csrf" and "_admin" to be
     *         used by common template code.
     */
    public static Map<String, Object> createBaseModel(final HttpSession session) {
        final Map<String, Object> model = Maps.newHashMap();

        final CsrfToken token = (CsrfToken) session.getAttribute("_csrf");
        if (token != null) {
            model.put("_csrf", token.getToken());
            model.put("_csrfParameterName", token.getParameterName());
        }

        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.getAuthorities().contains(ADMIN_AUTHORITY)) {
            model.put("_admin", true);
        }

        if (authentication != null && authentication.isAuthenticated()
                && !authentication.getAuthorities().contains(ANONYMOUS_AUTHORITY)) {
            model.put("_authenticated", true);
        }

        model.put("_menu", new ArrayList<NavigationMenuSection>());
        model.put("_vote", new ArrayList<NavigationMenuSection>());
        model.put("_results", new String());

        return model;
    }

}
