package edu.columbia.ase.teamproject.security;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.google.common.base.Joiner;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;

public class AuthKeyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserAccountDao userDao;

	private static final Logger logger =
			LoggerFactory.getLogger(AuthKeyAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		UsernamePasswordAuthenticationToken token =
				(UsernamePasswordAuthenticationToken) authentication;

		String username = token.getName();
		if (!username.equals("api")) {
			throw new BadCredentialsException("username must be api");
		}

		String credentials = (String) token.getCredentials();
		AuthKey authKey = new AuthKey(credentials);

		// TODO(pames): don't hard code this value.
		if (!authKey.isAuthKeyValid("secret")) {
			throw new BadCredentialsException("invalid MAC");
		}

		logger.info("Successful API login for user " + authKey.getId());
		UserAccount apiUser = userDao.find(authKey.getId());

		Collection<GrantedAuthority> authorities =
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						Joiner.on(",").join(apiUser.getPermissions()));
		User user = new User(apiUser.getUsername(), "[PROTECTED]", authorities);

		return new UsernamePasswordAuthenticationToken(user, null, authorities);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
