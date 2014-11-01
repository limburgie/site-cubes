package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;

import java.util.List;

public interface ContentItemService {

	List<ContentItem> getItems(Site site);

	ContentItem save(ContentItem item);

	void delete(ContentItem item);

}
