package edu.columbia.ase.teamproject.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class OpenIdAuthenticationTokenConsumerTest {

	private static final Logger logger =
			LoggerFactory.getLogger(OpenIdAuthenticationTokenConsumerTest.class);

	protected void setUp() throws Exception {
		logger.info("hello in setup");
	}

	@Test
	public void testFoo() {
		
	}
}
