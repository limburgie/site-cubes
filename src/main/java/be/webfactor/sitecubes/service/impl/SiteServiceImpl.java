package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.SiteRepository;
import be.webfactor.sitecubes.service.SiteService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class SiteServiceImpl implements SiteService {

	@Inject private SiteRepository repository;

	public List<Site> getSites() {
		return repository.findAll();
	}

}
