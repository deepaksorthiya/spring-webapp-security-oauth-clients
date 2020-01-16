package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author deepakk
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomAuthorizationRequestRepository customAuthorizationRequestRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// @formatter:off
		http
		.authorizeRequests().anyRequest().authenticated()
		.and()
		.oauth2Login()
		.authorizationEndpoint().authorizationRequestRepository(customAuthorizationRequestRepository)
		.and()
		.and();
		// @formatter:on

	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/favicon.ico/**");
	}

}
