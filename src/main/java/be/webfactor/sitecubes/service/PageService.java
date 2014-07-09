package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;

public interface PageService {

	Page getRoot();

	Page save(Page page);

	void delete(Page page);

	Page getPageById(long id);

	Page getPageByFriendlyUrl(String friendlyUrl);

	Page getFirstPage();

	/**
	 * Resets the page layout of all pages with the given layout to the default layout.
	 */
	void resetPageLayouts(PageLayout layout);

	void move(Page movedPage, Page targetParentPage, int position);

}
