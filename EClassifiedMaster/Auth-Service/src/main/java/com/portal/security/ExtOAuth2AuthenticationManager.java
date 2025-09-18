package com.portal.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class ExtOAuth2AuthenticationManager extends OAuth2AuthenticationManager implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
		response.setHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin,Access-Control-Allow-Credentials");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers",
				"X-Requested-With, Content-Type, Authorization, Origin, Accept, Access-Control-Request-Method, Access-Control-Request-Headers, Access-Control-Expose-Headers, Host, User-Agent, Accept-Language, Accept-Encoding, Connection, Pragma, Cache-Control, orgId,orgBpId,accessObjId,forceLogin");

		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("serial")
	public static class CustomAuthentication extends PreAuthenticatedAuthenticationToken {

		public CustomAuthentication(Object principal, Object credentials) {
			super(principal, credentials);
		}
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			return super.authenticate(authentication);
		} catch (Exception e) {
			return new CustomAuthentication(authentication.getPrincipal(), authentication.getCredentials());
		}
	}

}
