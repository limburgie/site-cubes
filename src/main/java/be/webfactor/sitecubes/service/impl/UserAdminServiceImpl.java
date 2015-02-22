package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.RoleService;
import be.webfactor.sitecubes.service.SiteService;
import be.webfactor.sitecubes.service.UserAdminService;
import be.webfactor.sitecubes.service.UserRoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;

@Named @Transactional(readOnly = true)
public class UserAdminServiceImpl implements UserAdminService {

	@Inject private RoleService roleService;
	@Inject private UserRoleService userRoleService;
	@Inject private SiteService siteService;

	public boolean isUserAdmin(User user, Site site) {
		Role admin = roleService.getRoleByName(Role.ADMIN_ROLE_NAME);
		boolean isUserAdmin = userRoleService.hasUserRole(user, admin);
		if (isUserAdmin) {
			return true;
		}
		if(site == null) {
			return false;
		}
		Role siteAdmin = roleService.getRoleByName(Role.SITE_ADMIN_ROLE_NAME);
		return userRoleService.hasUserSiteRole(user, site, siteAdmin);
	}

	public List<Site> getAdminSites(User user) {
		List<Site> result = new ArrayList<Site>();
		for (Site site : siteService.getSites()) {
			if (isUserAdmin(user, site)) {
				result.add(site);
			}
		}
		return result;
	}

}
