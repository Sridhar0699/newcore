package com.portal.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import com.portal.security.repo.AuthUmOrgRolesRepository;
import com.portal.security.repo.AuthUmOrgUsersRepository;
import com.portal.security.repo.LoginHistoryRepository;
import com.portal.security.repo.UserRepository;
import com.portal.security.util.CommonUtils;
import com.portal.security.util.DuplicateLoginException;
import com.portal.user.entities.UmOrgRoles;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final LoginHistoryRepository loginHistoryRepository;

	private final AuthUmOrgUsersRepository authUmOrgUsersRepository;
	private final AuthUmOrgRolesRepository authUmOrgRolesRepository;
	private final HttpServletRequest request;
	@Lazy
	@Autowired
	private TokenStore tokenStore;
	

	public CustomUserDetailsService(UserRepository userRepository, AuthUmOrgUsersRepository authUmOrgUsersRepository,
			AuthUmOrgRolesRepository authUmOrgRolesRepository, LoginHistoryRepository loginHistoryRepository,HttpServletRequest request) {
		this.userRepository = userRepository;
		this.authUmOrgUsersRepository = authUmOrgUsersRepository;
		this.authUmOrgRolesRepository = authUmOrgRolesRepository;
		this.loginHistoryRepository = loginHistoryRepository;
		this.request = request;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UmUsers umUsers = userRepository.findByLogonId(username.toUpperCase(), Arrays.asList(CommonUtils.getUserTypeId()));
		if (umUsers == null)
			throw new UsernameNotFoundException("Username " + username + " not found");
		if(getReqHeader("forceLogin") == null || (getReqHeader("forceLogin") != null && "false".equalsIgnoreCase(getReqHeader("forceLogin")))){
			List<Object[]> exitSession = loginHistoryRepository.getLoginSessionByUserName(username);
			if (exitSession != null && !exitSession.isEmpty())
				throw new DuplicateLoginException("You have logged in on another window or device, confirm to logout all active sessions and continue to login." );
		} else {
			List<Object[]> exitSession = loginHistoryRepository.getLoginSessionByUserName(username);
			if (exitSession != null && !exitSession.isEmpty()) {
				Object[] obj = exitSession.get(0);
				forceLogout(username, (String) obj[6]);
			}
		}
		List<UmOrgUsers> umOrgUsers = authUmOrgUsersRepository.findByUsers(umUsers.getUserId());
		List<Integer> roleIds = new ArrayList<>();
		for (UmOrgUsers uou : umOrgUsers) {
			roleIds.add(uou.getUmOrgRoles().getRoleId());
		}
		if (umUsers.getUserLockedTs() != null) {

			long currentTs = new Date().getTime();
			long lockedTs = umUsers.getUserLockedTs().getTime();
			Date afterAdding10Mins = new Date(lockedTs + (30 * 60 * 1000));

			if (currentTs >= afterAdding10Mins.getTime())
				umUsers.setUserLocked(false);
		}
		if (!roleIds.isEmpty()) {
			List<UmOrgRoles> umOrgRoles = (List<UmOrgRoles>) authUmOrgRolesRepository.findUmOrgRoles(roleIds);
			Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
			for (UmOrgRoles uor : umOrgRoles) {
				authorities.add(new SimpleGrantedAuthority(uor.getRoleShortId()));
			}
		}
		return new org.springframework.security.core.userdetails.User(umUsers.getLogonId(), umUsers.getPassword(), true,
				true, true, true, getGrantedAuthorities(umUsers));
	}

	private List<GrantedAuthority> getGrantedAuthorities(UmUsers user) {

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_TRUST_READ"));
		authorities.add(new SimpleGrantedAuthority("ROLE_TRUST_WRITE"));

		return authorities;
	}

	public String getReqHeader(String headerName) {
		try {
			return (request.getHeader(headerName) != null && !request.getHeader(headerName).isEmpty())
					? request.getHeader(headerName) : null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private boolean forceLogout(String userName, String refreshToken) {
		try {
			if (userName != null) {
				List<OAuth2AccessToken> accessTokens = (List<OAuth2AccessToken>) tokenStore.findTokensByClientIdAndUserName("hilvm", userName);
				if (!accessTokens.isEmpty()) {
					tokenStore.removeAccessToken(accessTokens.get(0));
					tokenStore.removeRefreshToken(accessTokens.get(0).getRefreshToken());
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
