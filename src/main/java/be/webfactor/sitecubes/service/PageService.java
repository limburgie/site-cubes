package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;

import java.util.List;

public interface PageService {

	List<Page> getPages();

	Page save(Page page);

	void delete(Page page);

	Page getPageById(long id);

	Page getPageByFriendlyUrl(String friendlyUrl);

	/**
	 * Resets the page layout of all pages with the given layout to the default layout.
	 */
	void resetPageLayouts(PageLayout layout);
}
