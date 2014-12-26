package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.domain.UserRole;

import java.util.List;

public interface UserRoleService {

	boolean hasUserRole(User user, Role role);

	boolean hasUserSiteRole(User user, Site site, Role role);

	List<UserRole> getUserRoles();

	UserRole save(UserRole userRole);

	void delete(UserRole userRole);

}
