package edu.columbia.ase.teamproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

// TODO: Auto-generated Javadoc
/**
 * The Class DummyAuthenticationProvider.
 */
public class DummyAuthenticationProvider implements AuthenticationProvider {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory
			.getLogger(DummyAuthenticationProvider.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see org.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		logger.info("Ignoring attempted authentication: {0}", authentication);
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.authentication.AuthenticationProvider#supports
	 * (java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("Claiming not to support {0}", authentication);
		return false;
	}

}
