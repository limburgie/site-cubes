package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.PageLayoutService;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class PageLayoutsBean implements Serializable {

	@Inject private PageLayoutService pageLayoutService;
	@Inject private FacesUtil facesUtil;

	private List<PageLayout> layouts;
	private PageLayout layout;

	@PostConstruct
	public void initLayouts() {
		layouts = pageLayoutService.getLayouts();
	}

	public void initNewLayout() {
		layout = new PageLayout();
	}

	public void save() {
		layout = pageLayoutService.save(layout);
		facesUtil.info("layout-saved-successfully");
	}

	public void delete() {
		pageLayoutService.delete(layout);
		layout = null;
		initLayouts();
		facesUtil.info("layout-deleted-successfully");
	}

	public void cancel() {
		layout = null;
		initLayouts();
	}

	public void selectLayout(PageLayout layout) {
		setLayout(SerializationUtils.clone(layout));
	}

	public void setLayout(PageLayout layout) {
		this.layout = layout;
	}

	public PageLayout getLayout() {
		return layout;
	}

	public List<PageLayout> getLayouts() {
		return layouts;
	}

}
