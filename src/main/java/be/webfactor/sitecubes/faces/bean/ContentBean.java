package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.ContentService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class ContentBean implements Serializable {

	@Inject private ContentService contentService;
	@Inject private FacesUtil facesUtil;

	private List<ContentItem> items;
	private ContentItem item;

	@PostConstruct
	public void initData() {
		items = contentService.getItems();
	}

	public void initNewItem() {
		item = new ContentItem();
	}

	public void save() {
		contentService.save(item);
		initData();
		facesUtil.info("content-saved-successfully");
	}

	public void cancel() {
		item = null;
	}

	public List<ContentItem> getItems() {
		return items;
	}

	public ContentItem getItem() {
		return item;
	}

}
