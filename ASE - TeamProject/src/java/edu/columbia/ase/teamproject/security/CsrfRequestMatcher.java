package edu.columbia.ase.teamproject.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.RequestMatcher;

import com.google.common.collect.ImmutableList;

// TODO: Auto-generated Javadoc
/**
 * The Class CsrfRequestMatcher.
 */
public class CsrfRequestMatcher implements RequestMatcher {

    /** The Constant CSRF_UNPROTECTED_METHODS. */
    private static final ImmutableList<String> CSRF_UNPROTECTED_METHODS = ImmutableList
            .<String> of("GET", "HEAD");

    /** The Constant CSRF_UNPROTECTED_URLS. */
    private static final ImmutableList<String> CSRF_UNPROTECTED_URLS = ImmutableList.<String> of(
            "/logout", "/apilogin.do");

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.springframework.security.web.util.RequestMatcher#matches(javax.servlet
     * .http.HttpServletRequest)
     */
    @Override
    public boolean matches(HttpServletRequest request) {
        String method = request.getMethod().toUpperCase();
        String path = request.getServletPath().toLowerCase();

        if (CSRF_UNPROTECTED_METHODS.contains(method) || CSRF_UNPROTECTED_URLS.contains(path)) {
            return false;
        }
        return true;
    }

}
