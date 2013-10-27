package edu.columbia.ase.teamproject.security;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

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
import com.google.common.io.BaseEncoding;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;

public class AuthKeyAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	UserAccountDao userDao;

	private static final Logger logger =
			LoggerFactory.getLogger(AuthKeyAuthenticationProvider.class);

	private class AuthKey {
		private final Long id;
		private final byte[] providedMac;

		/*
		 * authKey should be of the form:
		 * base64(id:base64(hmac(id, secret)))
		 */
		public AuthKey(String authKey) {
			String decoded = new String(BaseEncoding.base64().decode(authKey));
			String [] parts = decoded.split(":", 2);
			if (parts.length != 2) {
				throw new IllegalArgumentException();
			}
			this.id = Long.valueOf(parts[0]);
			this.providedMac = BaseEncoding.base64().decode(parts[1]);
		}

		public Long getId() {
			return id;
		}

		public boolean isAuthKeyValid(String secret) {
			try {
				Mac mac = Mac.getInstance("HmacSHA256");
				SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(),
						"HmacSHA256");
				mac.init(secretKey);
				mac.update(id.byteValue());
				byte [] expectedMac = mac.doFinal();
				if (expectedMac.length != providedMac.length) {
					return false;
				}
				int differences = 0;
				for (int i = 0; i < expectedMac.length; i++) {
					if (expectedMac[i] != providedMac[i]) {
						differences++;
					}
				}
				return differences == 0;
			} catch (NoSuchAlgorithmException e) {
				logger.warn(e.getMessage());
			} catch (InvalidKeyException e) {
				logger.warn(e.getMessage());
			}
			return false;
		}
	}

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
