package edu.columbia.ase.teamproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

public class DummyAuthenticationProvider implements AuthenticationProvider {

	private static final Logger logger =
			LoggerFactory.getLogger(DummyAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) {
		logger.info("Ignoring attempted authentication: {0}", authentication);
		return null;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("Claiming not to support {0}", authentication);
		return false;
	}

}
