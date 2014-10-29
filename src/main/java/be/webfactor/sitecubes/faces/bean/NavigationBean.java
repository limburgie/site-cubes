package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named @Scope("view")
public class NavigationBean implements Serializable {

	@Inject private PageService pageService;
	@Inject private SiteContextBean siteContextBean;

	private Page root;

	@PostConstruct
	public void initRoot() {
		root = pageService.getRoot(siteContextBean.getActiveSite());
	}

	public Page getRoot() {
		return root;
	}

}
