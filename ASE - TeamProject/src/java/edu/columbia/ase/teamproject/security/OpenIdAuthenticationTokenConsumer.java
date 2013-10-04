package edu.columbia.ase.teamproject.security;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

public class OpenIdAuthenticationTokenConsumer implements
	AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		throw new UnsupportedOperationException();
	}

}
