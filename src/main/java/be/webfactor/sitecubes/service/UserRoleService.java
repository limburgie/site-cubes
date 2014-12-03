package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;

public interface UserRoleService {

	boolean hasUserRole(User user, Role role);

	boolean hasUserSiteRole(User user, Site site, Role role);

	void setUserRole(User user, Role role);

	void setUserSiteRole(User user, Site site, Role role);

}
