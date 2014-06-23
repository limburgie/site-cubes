package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.repository.PageLayoutRepository;
import be.webfactor.sitecubes.service.PageLayoutService;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DefaultLayoutCannotBeDeletedException;
import be.webfactor.sitecubes.service.exception.DuplicatePageLayoutNameException;
import be.webfactor.sitecubes.service.exception.InvalidPageLayoutNameException;
import be.webfactor.sitecubes.service.exception.InvalidPageLayoutStructureException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class PageLayoutServiceImpl implements PageLayoutService {

	@Inject private PageService pageService;
	@Inject private PageLayoutRepository pageLayoutRepository;

	public List<PageLayout> getLayouts() {
		return pageLayoutRepository.findAll();
	}

	@Transactional
	public PageLayout save(PageLayout layout) {
		checkForValidName(layout);
		checkForDuplicateName(layout);
		checkForValidStructure(layout);
		PageLayout persisted = pageLayoutRepository.save(layout);
		if (layout.isDefaultLayout() || pageLayoutRepository.count() == 0) {
			setDefault(persisted);
		}
		return persisted;
	}

	private void setDefault(PageLayout layout) {
		pageLayoutRepository.undefaultAll();
		pageLayoutRepository.setDefault(layout.getId());
	}

	private void checkForValidStructure(PageLayout layout) {
		if (StringUtils.isBlank(layout.getStructure())) {
			throw new InvalidPageLayoutStructureException();
		}
	}

	private void checkForValidName(PageLayout layout) {
		if (StringUtils.isBlank(layout.getName())) {
			throw new InvalidPageLayoutNameException();
		}
	}

	private void checkForDuplicateName(PageLayout layout) {
		PageLayout layoutWithName = pageLayoutRepository.findByName(layout.getName());
		if (layoutWithName != null && !layoutWithName.equals(layout)) {
			throw new DuplicatePageLayoutNameException();
		}
	}

	@Transactional
	public void delete(PageLayout layout) {
		if (layout.isDefaultLayout()) {
			throw new DefaultLayoutCannotBeDeletedException();
		}
		pageService.resetPageLayouts(layout);
		pageLayoutRepository.delete(layout);
	}

	public PageLayout getLayout(long id) {
		return pageLayoutRepository.findOne(id);
	}

	public PageLayout getDefaultLayout() {
		List<PageLayout> defaultLayouts = pageLayoutRepository.getDefault();
		if (defaultLayouts.size() > 0) {
			return defaultLayouts.get(0);
		}
		return null;
	}

}
