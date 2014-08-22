package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.service.ContentItemService;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class ContentSelectorBean implements Serializable {

	private List<ContentItem> items;
	private ContentItem item;

	@Inject private ContentItemService contentItemService;
	@Inject private PageViewBean pageViewBean;

	public void initializeContent() {
		items = contentItemService.getItems();
	}

	public List<ContentItem> getItems() {
		return items;
	}

	public ContentItem getItem() {
		return item;
	}

	public void setItem(ContentItem item) {
		this.item = item;
	}

	public void onRowSelect(SelectEvent event) {
		item = (ContentItem) event.getObject();
		pageViewBean.addContent(item);
		item = null;
	}

}
