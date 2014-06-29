package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.repository.ContentLocationRepository;
import be.webfactor.sitecubes.service.ContentLocationService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ContentLocationServiceImpl implements ContentLocationService {

	@Inject private ContentLocationRepository contentLocationRepository;

	public List<ContentLocation> getLocationsOnPage(Page page) {
		return contentLocationRepository.findByPage(page);
	}

	@Transactional
	public void deleteItemLocations(ContentItem item) {
		contentLocationRepository.deleteItemLocations(item);
	}

	@Transactional
	public void moveLocation(long locationId, String toColumnId, int toPosition) {
		ContentLocation location = contentLocationRepository.findOne(locationId);
		String fromColumnId = location.getColumnId();
		int fromPosition = location.getPosition();

		contentLocationRepository.moveItemsInColumnDownFromPosition(toColumnId, toPosition);

		location.setColumnId(toColumnId);
		location.setPosition(toPosition);
		contentLocationRepository.saveAndFlush(location);

		contentLocationRepository.moveItemsInColumnUpFromPosition(fromColumnId, fromPosition);
	}

	@Transactional
	public void deletePageLocations(Page page) {
		contentLocationRepository.deletePageLocations(page);
	}

}
