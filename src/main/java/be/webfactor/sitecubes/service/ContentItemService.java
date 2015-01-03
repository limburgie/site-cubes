package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface ContentItemService {

	ContentItem getItemByTitle(Site site, String title);

	List<ContentItem> getItems(Site site);

	@PreAuthorize("hasPermission(#item.site, 'admin')")
	ContentItem save(ContentItem item);

	@PreAuthorize("hasPermission(#item.site, 'admin')")
	void delete(ContentItem item);

	@PreAuthorize("hasPermission(#site, 'admin')")
	void deleteSiteContent(Site site);

	ContentItem getItem(long itemId);

}
