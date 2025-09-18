package com.portal.security.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

import com.portal.gd.entities.GdAccessObjects;
import com.portal.security.repo.GdAccessObjectsRepository;
import com.portal.security.repo.UmOrgRolesPermissionsRepository;
import com.portal.security.repo.UmOrgUsersRepo;
import com.portal.security.repo.UserRepository;
import com.portal.user.entities.UmOrgRolesPermissions;
import com.portal.user.entities.UmOrgUsers;
import com.portal.user.entities.UmUsers;

@SuppressWarnings("rawtypes")
@Service
public class MinuteBasedVoter implements AccessDecisionVoter<Object> {

	@Autowired
	private GdAccessObjectsRepository gdAccessObjectsRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UmOrgUsersRepo umOrgUsersRepo;
	
	@Autowired
	private UmOrgRolesPermissionsRepository umOrgRolesPermissionsRepository;
	
	@SuppressWarnings({ "unused" })
	@Override
	public int vote(Authentication authentication, Object object, Collection collection) {
		FilterInvocation filterRequest = (FilterInvocation) object;
		String url = filterRequest.getRequestUrl();
		boolean NONanonymous = true;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			if (grantedAuthority.getAuthority().equalsIgnoreCase("ROLE_ANONYMOUS")) {
				NONanonymous = false;
			}
		}
		int vote = ACCESS_ABSTAIN;
		boolean NONexist = this.getAccessObjPermission(filterRequest.getHttpRequest(), authentication.getName());
		if (NONanonymous) {
			vote = ACCESS_DENIED;
			try {
				// below is how i match the role of current user and the role
				// that can access the current url
				/*
				 * for (int i = 0; i < urlroles.size(); i++) {
				 * 
				 * if (url.startsWith(urlroles.get(i).getUrl())) { NONexist =
				 * false; System.out.println("URL: " + url + " , Role: " +
				 * urlroles.get(i).getRole()); for (GrantedAuthority
				 * grantedAuthority : authorities) { if
				 * (grantedAuthority.getAuthority().equalsIgnoreCase(urlroles.
				 * get(i).getRole())) { vote = ACCESS_GRANTED; } }
				 * 
				 * } }
				 */
				// vote = ACCESS_ABSTAIN;
			} catch (Exception e) {
				System.out.println("Error at MinuteBasedVoter: " + e);
			}
			if (NONexist) {
				vote = ACCESS_GRANTED;
			}
		}
		return vote;
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean getAccessObjPermission(HttpServletRequest request, String logonId) {

		boolean isPermission = false;
		try {

			String orgId = null;
			String accessObjId = null;
			for (Enumeration<?> e = request.getHeaderNames(); e.hasMoreElements();) {
				String nextHeaderName = (String) e.nextElement();
				if ("org_id".equalsIgnoreCase(nextHeaderName))
					orgId = request.getHeader(nextHeaderName);
				if ("access_obj_id".equalsIgnoreCase(nextHeaderName))
					accessObjId = request.getHeader(nextHeaderName);
			}
			for (Map.Entry<String, String[]> reqParam : ((Map<String, String[]>)request.getParameterMap()).entrySet()) {
				if (orgId == null && "org_id".equalsIgnoreCase(reqParam.getKey()))
					orgId = reqParam.getValue()[0];
				if (accessObjId == null && "access_obj_id".equalsIgnoreCase(reqParam.getKey()))
					accessObjId = reqParam.getValue()[0];
			}

			GdAccessObjects gdAccessObjects = gdAccessObjectsRepo.getAccessObjectsById(accessObjId);
			if (gdAccessObjects != null) {
				if (!gdAccessObjects.isSecureGroup())
					return isPermission = true;

				if (orgId != null) {
					UmUsers umUsers = userRepository.findByLogonId(logonId);
					List<UmOrgUsers> umOrgUsers = umOrgUsersRepo.getOrgUsersByUserOrgId(umUsers.getUserId(), orgId, false);
					List<Integer> roleIds = new ArrayList<>();
					for (UmOrgUsers uOrgUser : umOrgUsers) {
						roleIds.add(uOrgUser.getUmOrgRoles().getRoleId());
						if (uOrgUser.getSecondaryRoles() != null && !uOrgUser.getSecondaryRoles().isEmpty())
						{
							for(String sRole : uOrgUser.getSecondaryRoles().split(",")){
								roleIds.add(Integer.parseInt(sRole));
							}
						}
						List<UmOrgRolesPermissions> permissions = umOrgRolesPermissionsRepository.getOrgRolePermissions(roleIds,accessObjId, false);
						for (UmOrgRolesPermissions permission : permissions)
							isPermission = permission.isPermissionLevel();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isPermission;
	}
}
