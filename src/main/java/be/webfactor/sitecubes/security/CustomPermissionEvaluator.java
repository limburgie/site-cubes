package be.webfactor.sitecubes.security;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.UserAdminService;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Inject private UserService userService;
	@Inject private UserAdminService userAdminService;

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		Site site = (targetDomainObject instanceof Site) ? (Site) targetDomainObject : null;
		User user = userService.getByUsername((String) authentication.getPrincipal());
		return userAdminService.isUserAdmin(user, site);
	}

	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}

}
