package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.PageService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named @Scope("view")
public class PageViewBean {

	@Inject private PageService pageService;
	@Inject private FacesUtil facesUtil;

	private Page page;

	@PostConstruct
	public void initPage() {
		String friendlyUrl = facesUtil.getParam("u");
		System.out.println("\n\n\n\n\n!!!!!!"+friendlyUrl+"!!!!!!\n\n\n\n");
		page = pageService.getPageByFriendlyUrl(friendlyUrl);
	}

	public Page getPage() {
		return page;
	}

}
