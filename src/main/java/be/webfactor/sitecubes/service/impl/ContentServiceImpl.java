package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.repository.ContentItemRepository;
import be.webfactor.sitecubes.service.ContentService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

	@Inject private ContentItemRepository contentItemRepository;

	public List<ContentItem> getItems() {
		return contentItemRepository.findAll();
	}

}
