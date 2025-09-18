package com.portal.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.portal.security.service.AuthUserDao;
import com.portal.user.entities.UmUsers;

@Component("authenticationProvider")
public class LimitLoginAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired(required = true)
	private AuthUserDao authUserDao;

	@Autowired
	@Override
	public void setUserDetailsService(UserDetailsService userDetailsService) {
		super.setUserDetailsService(userDetailsService);
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		try {

			Authentication auth = super.authenticate(authentication);

			// if reach here, means login success, else an exception will be
			// thrown
			// reset the user_attempts
			authUserDao.updateLogonRetries(authentication.getName(), "RESET");

			return auth;

		} catch (BadCredentialsException e) {
			String error = "";
			// invalid login, update to user_attempts
			authUserDao.updateLogonRetries(authentication.getName(), "FAILED");

			error = "Incorrect UserName or Password";
			throw new BadCredentialsException(error);

		} catch (LockedException e) {

			// this user is locked!
			String error = "";
			UmUsers umUsers = authUserDao.findByLogonIdUmUsers(authentication.getName());

			if (umUsers != null) {
				Date lastAttempts = umUsers.getUserLockedTs();
				error = "User account is locked! Username : " + authentication.getName() + ", Last Attempt : "
						+ lastAttempts;
			} else {
				error = e.getMessage();
			}

			throw new LockedException(error);
		}

	}

}