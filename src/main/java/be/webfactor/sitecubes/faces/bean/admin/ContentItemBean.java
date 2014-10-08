package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.ContentItemService;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class ContentItemBean implements Serializable {

	@Inject private ContentItemService contentItemService;
	@Inject private FacesUtil facesUtil;

	private List<ContentItem> items;
	private ContentItem item;

	@PostConstruct
	public void initData() {
		item = null;
		items = contentItemService.getItems();
	}

	public void initNewItem() {
		item = new ContentItem();
	}

	public void onRowSelect(SelectEvent event) {
		item = SerializationUtils.clone((ContentItem) event.getObject());
	}

	public void save() {
		contentItemService.save(item);
		facesUtil.info("content-saved-successfully");
	}

	public void delete() {
		contentItemService.delete(item);
		initData();
		facesUtil.info("content-deleted-successfully");
	}

	public void cancel() {
		initData();
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

}
