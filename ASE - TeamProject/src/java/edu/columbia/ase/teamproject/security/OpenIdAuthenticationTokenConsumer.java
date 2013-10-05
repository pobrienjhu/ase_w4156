package edu.columbia.ase.teamproject.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.google.common.base.Preconditions;

public class OpenIdAuthenticationTokenConsumer implements
	AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		Preconditions.checkState(
				token.getStatus().equals(OpenIDAuthenticationStatus.SUCCESS));
		// TODO(pames): be smarter about this by looking up or creating an
		// account as necessary, and getting their authority grants from there.
		return new User(token.getIdentityUrl(), "[PROTECTED]",
				AuthorityUtils.createAuthorityList("ROLE_USER"));
	}

}
