package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.repository.PageRepository;
import be.webfactor.sitecubes.service.ContentLocationService;
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

	@Inject private ContentLocationService contentLocationService;
	@Inject private PageLayoutService pageLayoutService;
	@Inject private PageRepository pageRepository;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;

	public List<Page> getRootPages() {
		return pageRepository.getRootPages();
	}

	public int getRootPageCount() {
		return pageRepository.countRootPages();
	}

	@Transactional
	public Page save(Page page) {
		checkForInvalidName(page);
		checkForInvalidFriendlyUrl(page);
		checkForDuplicateFriendlyUrl(page);
		if (page.getId() == null) {
			int position;
			if (page.getParent() == null) {
				position = getRootPageCount();
			} else {
				position = page.getParent().getChildren().size();
			}
			page.setPosition(position);
		}
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
		contentLocationService.deletePageLocations(page);
		Page parent = page.getParent();
		if (parent != null) {
			parent.removePage(page);
			pageRepository.save(parent);
		}
		pageRepository.delete(page);
		movePagesUpForParentFromPosition(parent, page.getPosition());
	}

	public Page getPageById(long id) {
		return pageRepository.findOne(id);
	}

	public Page getPageByFriendlyUrl(String friendlyUrl) {
		return pageRepository.findByFriendlyUrl(friendlyUrl);
	}

	public Page getFirstPage() {
		List<Page> pages = getRootPages();
		if (pages.isEmpty()) {
			return null;
		}
		return pages.get(0);
	}

	@Transactional
	public void resetPageLayouts(PageLayout layout) {
		PageLayout defaultLayout = pageLayoutService.getDefaultLayout();
		pageRepository.updatePageLayout(layout, defaultLayout);
	}

	@Transactional
	public void move(Page movedPage, Page targetParentPage, int position) {
		int oldPosition = movedPage.getPosition();
		Page oldParent = movedPage.getParent();
		movePagesDownForParentFromPosition(targetParentPage, position);
		movedPage.setPosition(position);
		movedPage.setParent(targetParentPage);
		pageRepository.saveAndFlush(movedPage);
		movePagesUpForParentFromPosition(oldParent, oldPosition);
	}

	private void movePagesDownForParentFromPosition(Page parent, int position) {
		List<Page> children = parent == null ? getRootPages() : parent.getChildren();
		for (int i=children.size()-1; i>=position; i--) {
			Page child = children.get(i);
			child.setPosition(i+1);
			pageRepository.saveAndFlush(child);
		}
	}

	private void movePagesUpForParentFromPosition(Page parent, int position) {
		List<Page> children = parent == null ? getRootPages() : parent.getChildren();
		for (int i=position+1; i<children.size(); i++) {
			Page child = children.get(i);
			child.setPosition(i-1);
			pageRepository.saveAndFlush(child);
		}
	}

}
