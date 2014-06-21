package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.service.PageLayoutService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class PageLayoutsBean implements Serializable {

	@Inject private PageLayoutService pageLayoutService;

	private List<PageLayout> layouts;
	private PageLayout layout;

	@PostConstruct
	public void initLayouts() {
		layouts = pageLayoutService.getLayouts();
	}

	public void initNewLayout() {
		layout = new PageLayout();
	}

	public void cancel() {
		layout = null;
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
