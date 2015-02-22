package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;

import java.util.List;

public interface ContentLocationService {

	ContentLocation save(ContentLocation location);

	List<ContentLocation> getLocationsOnPage(Page page);

	void deleteItemLocations(ContentItem item);

	void moveLocation(long locationId, String toColumnId, int position);

	void deletePageLocations(Page page);

	void deleteSiteLocations(Site site);

	void delete(ContentLocation location);

	ContentLocation getLocation(long id);

	ContentLocation addItemInFirstColumn(Page page, ContentItem item);

}
