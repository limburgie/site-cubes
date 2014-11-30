package be.webfactor.sitecubes.security;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.UserService;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
public class CustomPermissionEvaluator implements PermissionEvaluator {

	@Inject private UserService userService;

	public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
		String username = (String) authentication.getPrincipal();
		if (username != null) {
			User user = userService.getByUsername(username);
			if (targetDomainObject instanceof Site) {
				Site site = (Site) targetDomainObject;
			}
		}
		return false;
	}

	public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
		throw new UnsupportedOperationException();
	}

}
