package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.ContentLocationRepository;
import be.webfactor.sitecubes.service.ContentLocationService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ContentLocationServiceImpl implements ContentLocationService {

	@Inject private ContentLocationRepository contentLocationRepository;

	@Transactional @Secured("ROLE_ADMIN")
	public ContentLocation save(ContentLocation location) {
		return contentLocationRepository.save(location);
	}

	public List<ContentLocation> getLocationsOnPage(Page page) {
		return contentLocationRepository.findByPage(page);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void deleteItemLocations(ContentItem item) {
		contentLocationRepository.deleteItemLocations(item);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void moveLocation(long locationId, String toColumnId, int toPosition) {
		ContentLocation location = contentLocationRepository.findOne(locationId);
		Page page = location.getPage();
		String fromColumnId = location.getColumnId();
		int fromPosition = location.getPosition();

		doMove(location, null, -1);
		moveItemsInColumnUpFromPosition(page, fromColumnId, fromPosition + 1);
		moveItemsInColumnDownFromPosition(page, toColumnId, toPosition);
		doMove(location, toColumnId, toPosition);
	}

	private void doMove(ContentLocation location, String toColumnId, int toPosition) {
		location.setColumnId(toColumnId);
		location.setPosition(toPosition);
		contentLocationRepository.saveAndFlush(location);
	}

	private void moveItemsInColumnUpFromPosition(Page page, String columnId, int position) {
		List<ContentLocation> locations = contentLocationRepository.findByPageAndColumnIdFromPosition(page, columnId, position);
		for (int i = 0; i < locations.size(); i++) {
			ContentLocation location = locations.get(i);
			int current = location.getPosition();
			if (current >= position) {
				location.setPosition(current - 1);
				contentLocationRepository.saveAndFlush(location);
			}
		}
	}

	private void moveItemsInColumnDownFromPosition(Page page, String columnId, int position) {
		List<ContentLocation> locations = contentLocationRepository.findByPageAndColumnIdFromPosition(page, columnId, position);
		for (int i = locations.size() - 1; i >= 0; i--) {
			ContentLocation location = locations.get(i);
			location.setPosition(location.getPosition() + 1);
			contentLocationRepository.saveAndFlush(location);
		}
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void deletePageLocations(Page page) {
		contentLocationRepository.deletePageLocations(page);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void deleteSiteLocations(Site site) {
		contentLocationRepository.deleteSiteLocations(site);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(ContentLocation location) {
		Page page = location.getPage();
		String columnId = location.getColumnId();
		int position = location.getPosition();

		contentLocationRepository.delete(location);
		moveLocationsUpFromPosition(page, columnId, position);
	}

	private void moveLocationsUpFromPosition(Page page, String columnId, int position) {
		List<ContentLocation> locations = contentLocationRepository.findByPageAndColumnIdFromPosition(page, columnId, position);
		for (ContentLocation location : locations) {
			int currentPosition = location.getPosition();
			location.setPosition(currentPosition - 1);
			contentLocationRepository.saveAndFlush(location);
		}
	}

	public ContentLocation getLocation(long id) {
		return contentLocationRepository.findOne(id);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public ContentLocation addItemInFirstColumn(Page page, ContentItem item) {
		ContentLocation location = new ContentLocation();
		location.setPage(page);
		location.setItem(item);
		location.setPosition(contentLocationRepository.getLocationCountForPageInColumnId(page, "col1"));
		location.setColumnId("col1");
		return contentLocationRepository.save(location);
	}

}
