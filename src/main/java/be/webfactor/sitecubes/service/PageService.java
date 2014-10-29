package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.domain.Site;

public interface PageService {

	Page createRoot(Site site);

	Page getRoot(Site site);

	Page save(Page page);

	void delete(Page page);

	Page getPageById(long id);

	Page getPageByFriendlyUrl(Site site, String friendlyUrl);

	Page getFirstPage(Site site);

	/**
	 * Resets the page layout of all pages with the given layout to the default layout.
	 */
	void resetPageLayouts(PageLayout layout);

	void move(Page movedPage, Page targetParentPage, int position);

}
