package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.PageLayout;

import java.util.List;

public interface PageLayoutService {

	List<PageLayout> getLayouts();

	PageLayout save(PageLayout layout);

	void delete(PageLayout layout);

	PageLayout getLayout(long id);

	PageLayout getDefaultLayout();
}
