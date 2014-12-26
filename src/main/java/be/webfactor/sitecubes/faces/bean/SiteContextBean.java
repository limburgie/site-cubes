package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.ContentItemService;
import be.webfactor.sitecubes.service.SiteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named @Scope("view")
public class SiteContextBean implements Serializable {

	@Inject private SiteService siteService;
	@Inject private ContentItemService contentItemService;
	@Inject private FacesUtil facesUtil;

	private Site site;

	@PostConstruct
	public void init() {
		initSite();
	}

	public String getContentByTitle(String title) {
		ContentItem item = contentItemService.getItemByTitle(site, title);
		return item == null ? "" : item.getContent();
	}

	private void initSite() {
		String siteFriendlyUrl = facesUtil.getRequestParam("s");
		site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
		if (site == null) {
			site = siteService.getDefaultSite();
		}
	}

	public Site getSite() {
		return site;
	}

}
