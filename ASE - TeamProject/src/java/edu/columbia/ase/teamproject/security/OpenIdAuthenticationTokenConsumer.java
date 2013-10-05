package edu.columbia.ase.teamproject.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount.AccountType;

public class OpenIdAuthenticationTokenConsumer implements
	AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private static final Logger logger =
			LoggerFactory.getLogger(OpenIdAuthenticationTokenConsumer.class);

	private final UserAccountDao userAccountDao;

	@Autowired
	public OpenIdAuthenticationTokenConsumer(UserAccountDao userAccountDao) {
		this.userAccountDao = Preconditions.checkNotNull(userAccountDao);
	}

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		Preconditions.checkState(
				token.getStatus().equals(OpenIDAuthenticationStatus.SUCCESS));
		UserAccount account = userAccountDao.findAccountByNameAndType(
				token.getIdentityUrl(), AccountType.OPENID);

		if (account == null) {
			ImmutableList.Builder<Permission> permissionBuilder =
					ImmutableList.<Permission>builder();
			permissionBuilder.add(Permission.USER);

			// There is a race condition here, in that 2 users registering at
			// the same time may both become admins if there are no other
			// users, however the risk is very low.
			if (userAccountDao.getNumberOfUsers() == 0) {
				permissionBuilder.add(Permission.ADMIN);
			}

			logger.info("Creating new account for " + token.getIdentityUrl());
			account = new UserAccount(AccountType.OPENID, token.getIdentityUrl(),
					null, permissionBuilder.build());
			userAccountDao.add(account);
		}

		return new User(account.getUsername(), "[PROTECTED]",
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						Joiner.on(",").join(account.getPermissions())));
	}

}
