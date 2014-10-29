package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Site;

import java.util.List;

public interface SiteService {

	Site getDefaultSite();

	List<Site> getSites();

	Site save(Site site);

	Site getSiteByFriendlyUrl(String friendlyUrl);

	void delete(Site site);

}
