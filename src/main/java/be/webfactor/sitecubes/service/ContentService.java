package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;

import java.util.List;

public interface ContentService {

	List<ContentItem> getItems();

	ContentItem save(ContentItem item);

}