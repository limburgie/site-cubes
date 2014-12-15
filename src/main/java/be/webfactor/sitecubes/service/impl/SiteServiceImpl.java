package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.SiteRepository;
import be.webfactor.sitecubes.service.*;
import be.webfactor.sitecubes.service.exception.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class SiteServiceImpl implements SiteService {

	@Inject private PageService pageService;
	@Inject private SiteRepository repository;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;
	@Inject private ContentItemService contentItemService;
	@Inject private ThemeService themeService;

	@PostConstruct
	public void init() {
		if (getDefaultSite() == null) {
			Site defaultSite = Site.DEFAULT_SITE;
			defaultSite.setTheme(themeService.getDefault());
			save(defaultSite);
		}
	}

	public Site getDefaultSite() {
		return repository.getDefaultSite();
	}

	public List<Site> getSites() {
		return repository.findAll();
	}

	@Transactional @Secured("ROLE_ADMIN")
	public Site save(Site site) {
		validate(site);
		boolean isNew = site.isNew();
		site = repository.save(site);
		if (isNew) {
			pageService.createRoot(site);
		}
		return site;
	}

	private void validate(Site site) {
		checkForInvalidName(site);
		checkForInvalidFriendlyUrl(site);
		checkForDuplicateName(site);
		checkForDuplicateFriendlyUrl(site);
		checkForDuplicateVirtualHost(site);
	}

	private void checkForDuplicateVirtualHost(Site site) {
		if (StringUtils.isNotBlank(site.getVirtualHost())) {
			Site virtualHostSite = getSiteByVirtualHost(site.getVirtualHost());
			if (virtualHostSite != null && !virtualHostSite.equals(site)) {
				throw new DuplicateSiteVirtualHostException();
			}
		}
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

	public Site getSiteByVirtualHost(String virtualHost) {
		return repository.findByVirtualHost(virtualHost);
	}

	private Site getSiteByName(String name) {
		return repository.findByName(name);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(Site site) {
		if (site.isDefaultSite()) {
			throw new DefaultSiteCannotBeDeletedException();
		}
		contentItemService.deleteSiteContent(site);
		pageService.deleteSitePages(site);
		repository.delete(site);
	}

	public Site getSite(long id) {
		return repository.findOne(id);
	}

}
