package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.repository.PageRepository;
import be.webfactor.sitecubes.service.PageService;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Transactional(readOnly = true)
public class PageServiceImpl implements PageService, Serializable {

	@Inject private PageRepository pageRepository;

	public List<Page> getPages() {
		return pageRepository.getRootPages();
	}

	@Transactional
	public Page save(Page page) {
		return pageRepository.save(page);
	}

	@Transactional
	public void delete(Page page) {
		Page parent = page.getParent();
		if (parent != null) {
			parent.removePage(page);
			pageRepository.save(parent);
		}
		pageRepository.delete(page);
	}

}
