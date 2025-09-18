package com.portal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.portal.security.service.CustomUserDetailsService;

@SuppressWarnings("deprecation")
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	@Qualifier("authenticationProvider")
	LimitLoginAuthenticationProvider authenticationProvider;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/v1/user/forgotpwd")
		.antMatchers("/v1/users/resetpwd/***")
		.antMatchers("/v1/users/resetpwd")
		.antMatchers("/v1/resetPassword")
				.antMatchers("/v1/user/resetpwd").antMatchers("/v1/users/reset/**").antMatchers("/swagger**/**/**")
				.antMatchers("/webjars/**").antMatchers("/api-docs").antMatchers("/v1/process/**/**")
				.antMatchers("/socket").antMatchers("/v1/user/registration").antMatchers("/v1/user/create/customer")
				.antMatchers("v1/process/captcha").antMatchers("/v1/rms/validate/accesskey").antMatchers("/v1/wf/updateEvent/byemail");
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		authenticationProvider.setUserDetailsService(userDetailsService);
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
