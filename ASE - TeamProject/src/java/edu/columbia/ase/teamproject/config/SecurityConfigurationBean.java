package edu.columbia.ase.teamproject.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import edu.columbia.ase.teamproject.security.DummyAuthenticationProvider;
import edu.columbia.ase.teamproject.security.DummyUserDetailsService;
import edu.columbia.ase.teamproject.security.OpenIdAuthenticationTokenConsumer;

@Configuration
@EnableWebSecurity
public class SecurityConfigurationBean extends WebSecurityConfigurerAdapter {

	private static final Logger logger =
			LoggerFactory.getLogger(SecurityConfigurationBean.class);

	private final OpenIdAuthenticationTokenConsumer openIdUserDetailsProvider;

	public SecurityConfigurationBean() {
		this.openIdUserDetailsProvider =
				new OpenIdAuthenticationTokenConsumer();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		logger.info("configure()");
		http
			.authorizeUrls()
				.antMatchers("/**")
					.denyAll()
				.antMatchers("/mvc/controller/persons")
					.permitAll()
				.antMatchers("/mvc/controller/soy")
					.hasRole("USER")
			.and()
			.openidLogin()
				.authenticationUserDetailsService(openIdUserDetailsProvider)
				.permitAll(true)
			.and()
			.authenticationProvider(new DummyAuthenticationProvider())
			.userDetailsService(new DummyUserDetailsService());
	}
}
