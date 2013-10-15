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
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;

@Transactional
public class OpenIdAuthenticationTokenConsumer implements
	AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

	private static final Logger logger =
			LoggerFactory.getLogger(OpenIdAuthenticationTokenConsumer.class);
	
	private static final Object lock = new Object();
	
	private final UserAccountDao userAccountDao;

	@Autowired
	public OpenIdAuthenticationTokenConsumer(UserAccountDao userAccountDao) {
		this.userAccountDao = Preconditions.checkNotNull(userAccountDao);
	}

	@Override
	public UserDetails loadUserDetails(OpenIDAuthenticationToken token)
			throws UsernameNotFoundException {
		Preconditions.checkArgument(
				token.getStatus().equals(OpenIDAuthenticationStatus.SUCCESS));
		UserAccount account = userAccountDao.findAccountByNameAndType(
				token.getIdentityUrl(), AccountType.OPENID);

			if (account == null) {
				ImmutableList.Builder<Permission> permissionBuilder =
						ImmutableList.<Permission>builder();
				permissionBuilder.add(Permission.USER);

				/* 
				 * Double-check idiom for lazy initialization of instance fields
				 * (works in this case too!)
				 * 
				 * see Effective Java Joshua Bloch Item #71
				 * 
				 * First we check if the number of users is 0.
				 * If that is not the case we don't consider this any further. 
				 */
				if (userAccountDao.getNumberOfUsers() == 0) {
					/*
					 * if there are no users we synchronize on the 
					 * lock object. 
					 * We only synchronize in the case no users are found
					 */
					synchronized(lock) {
						/*
						 * when we have gotten to this point, 
						 * we need to double check that the number of 
						 * users is still 0. 
						 * This is because this thread could have been
						 * waiting for the lock to free up. If that were the 
						 * case then the first user admin would have already 
						 * been set. 
						 */
						if (userAccountDao.getNumberOfUsers() == 0) {
							permissionBuilder.add(Permission.ADMIN);
							logger.info("Admin permission set");
						}
					}
				}

				logger.info("Creating new account for " +
						token.getIdentityUrl());
				account = new UserAccount(AccountType.OPENID,
						token.getIdentityUrl(), token.getName(), null,
						permissionBuilder.build());

				userAccountDao.add(account);
				
			}
		//}

		return new User(account.getUsername(), "[PROTECTED]",
				AuthorityUtils.commaSeparatedStringToAuthorityList(
						Joiner.on(",").join(account.getPermissions())));
	}

}
