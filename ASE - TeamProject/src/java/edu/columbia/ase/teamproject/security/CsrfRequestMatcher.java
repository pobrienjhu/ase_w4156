package edu.columbia.ase.teamproject.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.RequestMatcher;

import com.google.common.collect.ImmutableList;

public class CsrfRequestMatcher implements RequestMatcher {

	private static final ImmutableList<String> CSRF_UNPROTECTED_METHODS =
			ImmutableList.<String>of("GET", "HEAD");
	private static final ImmutableList<String> CSRF_UNPROTECTED_URLS =
			ImmutableList.<String>of("/logout");

	@Override
	public boolean matches(HttpServletRequest request) {
		String method = request.getMethod().toUpperCase();
		String path = request.getServletPath().toLowerCase();

		if (CSRF_UNPROTECTED_METHODS.contains(method) ||
				CSRF_UNPROTECTED_URLS.contains(path)) {
			return false;
		}
		return true;
	}

}
