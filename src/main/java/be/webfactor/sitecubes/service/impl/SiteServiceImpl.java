package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.SiteRepository;
import be.webfactor.sitecubes.service.SiteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class SiteServiceImpl implements SiteService {

	@Inject private SiteRepository repository;

	public List<Site> getSites() {
		return repository.findAll();
	}

	@Transactional @Secured("ROLE_ADMIN")
	public Site save(Site site) {
		return repository.save(site);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(Site site) {
		repository.delete(site);
	}

}
