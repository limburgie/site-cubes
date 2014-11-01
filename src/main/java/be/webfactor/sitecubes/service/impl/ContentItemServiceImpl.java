package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.ContentItemRepository;
import be.webfactor.sitecubes.service.ContentItemService;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.exception.InvalidContentTitleException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ContentItemServiceImpl implements ContentItemService {

	@Inject private ContentLocationService contentLocationService;
	@Inject private ContentItemRepository contentItemRepository;

	public List<ContentItem> getItems(Site site) {
		return contentItemRepository.getSiteContent(site);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public ContentItem save(ContentItem item) {
		validateTitle(item);
		if (item.getId() == null) {
			item.setCreateDate(new Date());
		}
		item.setModifiedDate(new Date());
		return contentItemRepository.save(item);
	}

	private void validateTitle(ContentItem item) {
		if (StringUtils.isBlank(item.getTitle())) {
			throw new InvalidContentTitleException();
		}
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(ContentItem item) {
		contentLocationService.deleteItemLocations(item);
		contentItemRepository.delete(item);
	}

}
