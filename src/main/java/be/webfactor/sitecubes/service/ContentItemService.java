package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;

import java.util.List;

public interface ContentItemService {

	ContentItem getItem(long id);

	List<ContentItem> getItems();

	ContentItem save(ContentItem item);

	void delete(ContentItem item);

}
