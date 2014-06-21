package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.repository.PageLayoutRepository;
import be.webfactor.sitecubes.service.PageLayoutService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class PageLayoutServiceImpl implements PageLayoutService {

	@Inject private PageLayoutRepository pageLayoutRepository;

	public List<PageLayout> getLayouts() {
		return pageLayoutRepository.findAll();
	}

}
