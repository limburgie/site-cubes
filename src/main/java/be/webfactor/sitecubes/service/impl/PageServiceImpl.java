package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.repository.PageRepository;
import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import be.webfactor.sitecubes.service.PageLayoutService;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DuplicateFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidPageNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Transactional(readOnly = true)
public class PageServiceImpl implements PageService, Serializable {

	@Inject private PageLayoutService pageLayoutService;
	@Inject private PageRepository pageRepository;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;

	public List<Page> getPages() {
		return pageRepository.getRootPages();
	}

	@Transactional
	public Page save(Page page) {
		checkForInvalidName(page);
		checkForInvalidFriendlyUrl(page);
		checkForDuplicateFriendlyUrl(page);
		return pageRepository.save(page);
	}

	private void checkForInvalidFriendlyUrl(Page page) {
		if (!friendlyUrlHandler.isValid(page.getFriendlyUrl())) {
			throw new InvalidFriendlyUrlException();
		}
	}

	private void checkForInvalidName(Page page) {
		if (StringUtils.isBlank(page.getName())) {
			throw new InvalidPageNameException();
		}
	}

	private void checkForDuplicateFriendlyUrl(Page page) {
		Page friendlyUrlPage = getPageByFriendlyUrl(page.getFriendlyUrl());
		if (friendlyUrlPage != null && !friendlyUrlPage.equals(page)) {
			throw new DuplicateFriendlyUrlException();
		}
	}

	@Transactional
	public void delete(Page page) {
		//TODO: Delete content locations
		Page parent = page.getParent();
		if (parent != null) {
			parent.removePage(page);
			pageRepository.save(parent);
		}
		pageRepository.delete(page);
	}

	public Page getPageById(long id) {
		return pageRepository.findOne(id);
	}

	public Page getPageByFriendlyUrl(String friendlyUrl) {
		return pageRepository.findByFriendlyUrl(friendlyUrl);
	}

	@Transactional
	public void resetPageLayouts(PageLayout layout) {
		PageLayout defaultLayout = pageLayoutService.getDefaultLayout();
		pageRepository.updatePageLayout(layout, defaultLayout);
	}

}
