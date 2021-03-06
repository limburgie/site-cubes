package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.domain.UserRole;
import be.webfactor.sitecubes.repository.UserRoleRepository;
import be.webfactor.sitecubes.service.UserRoleService;
import be.webfactor.sitecubes.service.exception.DuplicateRoleAssignmentException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {

	@Inject private UserRoleRepository userRoleRepository;

	public boolean hasUserRole(User user, Role role) {
		if (user == null || role == null) {
			return false;
		}
		return userRoleRepository.getUserWithRoleCount(user, role) > 0;
	}

	public boolean hasUserSiteRole(User user, Site site, Role role) {
		if (user == null || site == null || role == null) {
			return false;
		}
		return userRoleRepository.getUserWithSiteRoleCount(user, site, role) > 0;
	}

	public List<UserRole> getUserRoles() {
		return userRoleRepository.findAll();
	}

	@Transactional @Secured("ROLE_ADMIN")
	public UserRole save(UserRole userRole) {
		User user = userRole.getUser();
		Role role = userRole.getRole();
		Site site = userRole.getSite();

		if (hasUserRole(user, role) || hasUserSiteRole(user, site, role)) {
			throw new DuplicateRoleAssignmentException();
		}
		return userRoleRepository.save(userRole);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(UserRole userRole) {
		userRoleRepository.delete(userRole);
	}

}
