package edu.columbia.ase.teamproject.security;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.openid.OpenIDAttribute;
import org.springframework.security.openid.OpenIDAuthenticationStatus;
import org.springframework.security.openid.OpenIDAuthenticationToken;

import com.google.common.collect.ImmutableList;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;

@RunWith(BlockJUnit4ClassRunner.class)
public class OpenIdAuthenticationTokenConsumerTest {

	private OpenIdAuthenticationTokenConsumer tokenConsumer;

	@Mock
	UserAccountDao mockAccountDao;
	@Mock
	OpenIDAuthenticationToken mockToken;
	@Mock
	OpenIDAttribute mockAttribute;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		this.tokenConsumer = new OpenIdAuthenticationTokenConsumer(
				mockAccountDao);
	}

	@Test
	public void testUnsuccessfulAuthenticationThrowsException() {
		OpenIDAuthenticationStatus[] statuses = OpenIDAuthenticationStatus
				.values();
		for (OpenIDAuthenticationStatus status : statuses) {
			if (status.equals(OpenIDAuthenticationStatus.SUCCESS)) {
				continue;
			}
			when(mockToken.getStatus()).thenReturn(status);
			try {
				tokenConsumer.loadUserDetails(mockToken);
				Assert.fail("Should not accept status: " + status.toString());
			} catch (IllegalArgumentException ex) {
				// expected.
			}
		}
	}

	@Test
	public void testFirstUserGetsAdminPrivileges() {
		when(mockToken.getStatus()).thenReturn(
				OpenIDAuthenticationStatus.SUCCESS);
		when(mockToken.getIdentityUrl()).thenReturn("http://localhost");
		when(mockToken.getAttributes()).thenReturn(
				ImmutableList.of(mockAttribute));
		when(mockAttribute.getName()).thenReturn("email");
		when(mockAttribute.getValues()).thenReturn(
				ImmutableList.of("user@example.com"));
		when(mockAccountDao.getNumberOfUsers()).thenReturn(0L);
		UserDetails details = tokenConsumer.loadUserDetails(mockToken);
		GrantedAuthority expected = AuthorityUtils.createAuthorityList("ADMIN")
				.get(0);
		Assert.assertTrue(details.getAuthorities().contains(expected));
	}

	@Test
	public void testSecondUserDoesNotGetAdminPrivileges() {
		when(mockToken.getStatus()).thenReturn(
				OpenIDAuthenticationStatus.SUCCESS);
		when(mockToken.getIdentityUrl()).thenReturn("http://localhost");
		when(mockToken.getAttributes()).thenReturn(
				ImmutableList.of(mockAttribute));
		when(mockAttribute.getName()).thenReturn("email");
		when(mockAttribute.getValues()).thenReturn(
				ImmutableList.of("user@example.com"));
		when(mockAccountDao.getNumberOfUsers()).thenReturn(1L);
		UserDetails details = tokenConsumer.loadUserDetails(mockToken);
		GrantedAuthority expected = AuthorityUtils.createAuthorityList("USER")
				.get(0);
		GrantedAuthority unexpected = AuthorityUtils.createAuthorityList(
				"ADMIN").get(0);

		Assert.assertTrue(details.getAuthorities().contains(expected));
		Assert.assertFalse(details.getAuthorities().contains(unexpected));
	}

}
