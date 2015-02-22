package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.security.UserAuthenticationService;
import be.webfactor.sitecubes.service.UserAdminService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class PermissionBean implements Serializable {

	@Inject private SiteContextBean siteContextBean;
	@Inject private UserAuthenticationService userAuthenticationService;
	@Inject private UserAdminService userAdminService;

	private User user;
	private Site site;
	private boolean admin;
	private boolean siteAdmin;
	private List<Site> myOtherSites;

	@PostConstruct
	public void init() {
		user = userAuthenticationService.getAuthenticatedUser();
		site = siteContextBean.getSite();
		initAdmin();
		initSiteAdmin();
		initMyOtherSites();
	}

	private void initMyOtherSites() {
		myOtherSites = userAdminService.getAdminSites(user);
		myOtherSites.remove(site);
	}

	private void initAdmin() {
		admin = userAdminService.isUserAdmin(user, null);
	}

	private void initSiteAdmin() {
		if (user == null) {
			siteAdmin = false;
		} else {
			siteAdmin = userAdminService.isUserAdmin(user, site);
		}
	}

	public boolean isSiteAdmin() {
		return siteAdmin;
	}

	public boolean isAdmin() {
		return admin;
	}

	public List<Site> getMyOtherSites() {
		return myOtherSites;
	}
}
