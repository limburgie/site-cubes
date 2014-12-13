package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.*;
import be.webfactor.sitecubes.repository.UserRoleRepository;
import be.webfactor.sitecubes.service.UserRoleService;
import be.webfactor.sitecubes.service.exception.InvalidRoleAssignmentException;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class UserRoleServiceImpl implements UserRoleService {

	@Inject private UserRoleRepository userRoleRepository;

	public List<UserRole> getRoles(User user) {
		return userRoleRepository.getUserRoles(user);
	}

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

	@Transactional
	public void setUserRole(User user, Role role) {
		if (role.getType() != RoleType.GLOBAL) {
			throw new InvalidRoleAssignmentException();
		}
		if (!hasUserRole(user, role)) {
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
		}
	}

	@Transactional
	public void setUserSiteRole(User user, Site site, Role role) {
		if (role.getType() != RoleType.SITE) {
			throw new InvalidRoleAssignmentException();
		}
		if (!hasUserSiteRole(user, site, role)) {
			UserRole userRole = new UserRole();
			userRole.setUser(user);
			userRole.setSite(site);
			userRole.setRole(role);
			userRoleRepository.save(userRole);
		}
	}

}
