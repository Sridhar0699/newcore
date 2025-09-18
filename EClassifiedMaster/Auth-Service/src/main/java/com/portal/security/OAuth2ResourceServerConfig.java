package com.portal.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import com.portal.security.util.MinuteBasedVoter;

@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	private static final String RESOURCE_ID = "my_rest_api";

	private ExtOAuth2AuthenticationManager customFilter = new ExtOAuth2AuthenticationManager();

	@Autowired
	private MinuteBasedVoter minuteBasedVoter;

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		resources.resourceId(RESOURCE_ID).stateless(false);
		resources.authenticationManager(customFilter);
	}

	// @Override
	public void configureOld(HttpSecurity http) throws Exception {
		http.csrf().disable().anonymous().disable().requestMatchers()
				.antMatchers("/test*", "/test/**/", "/test/**/**/**")
				.antMatchers("/v1/gd/*", "/v1/gd/*/**", "/v1/gd/*/**/***", "/v1/gd/*/**/**/***")
				.antMatchers("/v1/user/", "/v1/user/**/", "/v1/user/**/**/**")
				.antMatchers("/v1/org/", "/v1/org/**/", "/v1/org/**/**/**")
				.antMatchers("/oauth/revoke-token*", "/oauth/revoke-token/**", "/oauth/revoke-token**/**/**/**").and()
				.authorizeRequests().antMatchers(HttpMethod.GET, "/test*", "/test/*/", "/test/*/*/*")
				.access("hasRole('ROLE_USER')")
				.antMatchers("/v1/gd/*", "/v1/gd/*/**", "/v1/gd/*/**/***", "/v1/gd/*/**/**/***")
				.access("hasRole('ROLE_USER')").antMatchers("/v1/user/", "/v1/user/**/", "/v1/user/**/**/**")
				.access("hasRole('ROLE_USER')").antMatchers("/v1/org/", "/v1/org/**/", "/v1/org/**/**/**")
				.access("hasRole('ROLE_USER')")
				.antMatchers(HttpMethod.POST, "/test*", "/test/*/", "/test/*/*/*", "v1/erp/tt").permitAll().and()
				.exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
		http.authorizeRequests().anyRequest().authenticated().and().addFilterAfter(customFilter,
				AbstractPreAuthenticatedProcessingFilter.class);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.anonymous().disable().authorizeRequests().antMatchers("/oauth/token").permitAll().anyRequest()
				.fullyAuthenticated().accessDecisionManager(accessDecisionManager()).and().exceptionHandling()
				.accessDeniedHandler(new OAuth2AccessDeniedHandler()).and()
				.addFilterAfter(customFilter, AbstractPreAuthenticatedProcessingFilter.class);

	}

	@Bean
	public AccessDecisionManager accessDecisionManager() {

		System.out.println("Arrive AccessDecisionManager");

		List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList(new RoleVoter(),
				new AuthenticatedVoter(), minuteBasedVoter);

		System.out.println("End of AccessDecisionManager: " + decisionVoters);
		return new UnanimousBased(decisionVoters);
	}

}
