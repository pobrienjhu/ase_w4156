package edu.columbia.ase.teamproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class DummyUserDetailsService implements UserDetailsService {

	private static final Logger logger =
			LoggerFactory.getLogger(DummyUserDetailsService.class);

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		logger.info("loadUserByUsername: {0}", username);
		// this should never be called, since DummyAuthenticationProvider
		// never claims to support any authentication type.
		throw new UnsupportedOperationException();
	}
}
