package be.webfactor.sitecubes.security;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.RoleService;
import be.webfactor.sitecubes.service.UserRoleService;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Inject private RoleService roleService;
	@Inject private UserService userService;
	@Inject private UserRoleService userRoleService;

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		Role admin = roleService.getRoleByName(Role.ADMIN_ROLE_NAME);
		User user = userService.getByUsername((String) authentication.getPrincipal());

		if (userRoleService.hasUserRole(user, admin)) {
			return true;
		}

		if (targetDomainObject instanceof Site) {
			Role siteAdmin = roleService.getRoleByName(Role.SITE_ADMIN_ROLE_NAME);
			Site site = (Site) targetDomainObject;
			return userRoleService.hasUserSiteRole(user, site, siteAdmin);
		}
		return false;
	}

	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}

}
