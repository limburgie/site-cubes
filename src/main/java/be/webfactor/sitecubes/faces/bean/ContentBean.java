package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.service.ContentService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Scope("view")
public class ContentBean {

	@Inject private ContentService contentService;

	private List<ContentItem> items;

	@PostConstruct
	public void initData() {
		items = contentService.getItems();
	}

	public List<ContentItem> getItems() {
		return items;
	}

}
