package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.SiteRepository;
import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import be.webfactor.sitecubes.service.SiteService;
import be.webfactor.sitecubes.service.exception.DuplicateSiteFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.DuplicateSiteNameException;
import be.webfactor.sitecubes.service.exception.InvalidSiteFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidSiteNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class SiteServiceImpl implements SiteService {

	@Inject private SiteRepository repository;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;

	public List<Site> getSites() {
		return repository.findAll();
	}

	@Transactional @Secured("ROLE_ADMIN")
	public Site save(Site site) {
		validate(site);
		return repository.save(site);
	}

	private void validate(Site site) {
		checkForInvalidName(site);
		checkForInvalidFriendlyUrl(site);
		checkForDuplicateName(site);
		checkForDuplicateFriendlyUrl(site);
	}

	private void checkForDuplicateName(Site site) {
		Site nameSite = getSiteByName(site.getName());
		if (nameSite != null && !nameSite.equals(site)) {
			throw new DuplicateSiteNameException();
		}
	}

	private void checkForInvalidFriendlyUrl(Site site) {
		if (!friendlyUrlHandler.isValid(site.getFriendlyUrl())) {
			throw new InvalidSiteFriendlyUrlException();
		}
	}

	private void checkForInvalidName(Site site) {
		if (StringUtils.isBlank(site.getName())) {
			throw new InvalidSiteNameException();
		}
	}

	private void checkForDuplicateFriendlyUrl(Site site) {
		Site friendlyUrlSite = getSiteByFriendlyUrl(site.getFriendlyUrl());
		if (friendlyUrlSite != null && !friendlyUrlSite.equals(site)) {
			throw new DuplicateSiteFriendlyUrlException();
		}
	}

	public Site getSiteByFriendlyUrl(String friendlyUrl) {
		return repository.findByFriendlyUrl(friendlyUrl);
	}

	private Site getSiteByName(String name) {
		return repository.findByName(name);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(Site site) {
		repository.delete(site);
	}

}
