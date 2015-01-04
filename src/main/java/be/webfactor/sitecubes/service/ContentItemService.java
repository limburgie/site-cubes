package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;

import java.util.List;

public interface ContentItemService {

	ContentItem getItemByTitle(Site site, String title);

	List<ContentItem> getItems(Site site);

	ContentItem save(ContentItem item);

	void delete(ContentItem item);

	void deleteSiteContent(Site site);

	ContentItem getItem(long itemId);

}
