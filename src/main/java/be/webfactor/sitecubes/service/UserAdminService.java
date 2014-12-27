package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;

import java.util.List;

public interface UserAdminService {

	boolean isUserAdmin(User user, Site site);

	List<Site> getAdminSites(User user);

}
