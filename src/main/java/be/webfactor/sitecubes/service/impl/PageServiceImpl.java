package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.PageRepository;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import be.webfactor.sitecubes.service.PageLayoutService;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DuplicatePageFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidPageFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidPageNameException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@Transactional(readOnly = true)
public class PageServiceImpl implements PageService, Serializable {

	@Inject private ContentLocationService contentLocationService;
	@Inject private PageLayoutService pageLayoutService;
	@Inject private PageRepository pageRepository;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;

	@Transactional
	public Page createRoot(Site site) {
		Page root = getRoot(site);
		if (root != null) {
			return root;
		}
		root = new Page();
		root.setName(Page.ROOT_NAME);
		root.setFriendlyUrl(Page.ROOT_FRIENDLY_URL);
		root.setSite(site);
		root.setLayout(pageLayoutService.getDefaultLayout());
		return save(root);
	}

	public Page getRoot(Site site) {
		return getPageByFriendlyUrl(site, Page.ROOT_FRIENDLY_URL);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public Page save(Page page) {
		validate(page);
		if (page.getId() == null) {
			page.setPosition(getNewPosition(page));
		}
		return pageRepository.save(page);
	}

	private void validate(Page page) {
		checkForInvalidName(page);
		checkForInvalidFriendlyUrl(page);
		checkForDuplicateFriendlyUrl(page);
	}

	private int getNewPosition(Page page) {
		Page parent = page.getParent();
		return parent == null ? 0 : parent.getChildren().size();
	}

	private void checkForInvalidFriendlyUrl(Page page) {
		if (!friendlyUrlHandler.isValid(page.getFriendlyUrl())) {
			throw new InvalidPageFriendlyUrlException();
		}
	}

	private void checkForInvalidName(Page page) {
		if (StringUtils.isBlank(page.getName())) {
			throw new InvalidPageNameException();
		}
	}

	private void checkForDuplicateFriendlyUrl(Page page) {
		Page friendlyUrlPage = getPageByFriendlyUrl(page.getSite(), page.getFriendlyUrl());
		if (friendlyUrlPage != null && !friendlyUrlPage.equals(page)) {
			throw new DuplicatePageFriendlyUrlException();
		}
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(Page page) {
		contentLocationService.deletePageLocations(page);
		Page parent = page.getParent();
		if (parent != null) {
			parent.removePage(page);
			pageRepository.save(parent);
		}
		pageRepository.delete(page);
		movePagesUpForParentFromPosition(page.getSite(), parent, page.getPosition());
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void deleteSitePages(Site site) {
		pageRepository.deleteSitePages(site);
	}

	public Page getPageById(long id) {
		return pageRepository.findOne(id);
	}

	public Page getPageByFriendlyUrl(Site site, String friendlyUrl) {
		return pageRepository.findByFriendlyUrl(site, friendlyUrl);
	}

	public Page getFirstPage(Site site) {
		List<Page> pages = getRoot(site).getChildren();
		if (pages.isEmpty()) {
			return null;
		}
		return pages.get(0);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void resetPageLayouts(PageLayout layout) {
		PageLayout defaultLayout = pageLayoutService.getDefaultLayout();
		pageRepository.updatePageLayout(layout, defaultLayout);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void move(Page movingPage, Page targetParentPage, int position) {
		int oldPosition = movingPage.getPosition();
		Page oldParent = movingPage.getParent();
		doMovePage(movingPage, null, -1);
		movePagesUpForParentFromPosition(movingPage.getSite(), oldParent, oldPosition + 1);
		movePagesDownForParentFromPosition(movingPage.getSite(), targetParentPage, position);
		doMovePage(movingPage, targetParentPage, position);
	}

	private void movePagesUpForParentFromPosition(Site site, Page parent, int position) {
		List<Page> children = pageRepository.getPagesForParent(site, parent);
		for (int i = 0; i < children.size(); i++) {
			Page child = children.get(i);
			int current = child.getPosition();
			if (current >= position) {
				child.setPosition(current - 1);
				pageRepository.saveAndFlush(child);
			}
		}
	}

	private void movePagesDownForParentFromPosition(Site site, Page parent, int position) {
		List<Page> children = pageRepository.getPagesForParent(site, parent);
		for (int i = children.size() - 1; i >= position; i--) {
			Page child = children.get(i);
			child.setPosition(i + 1);
			pageRepository.saveAndFlush(child);
		}
	}

	private void doMovePage(Page page, Page parent, int position) {
		page.setParent(parent);
		page.setPosition(position);
		pageRepository.saveAndFlush(page);
	}

}
