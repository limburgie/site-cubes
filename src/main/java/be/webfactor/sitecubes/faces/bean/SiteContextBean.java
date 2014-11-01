package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.SiteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named @Scope("view")
public class SiteContextBean implements Serializable {

	@Inject private SiteService siteService;
	@Inject private FacesUtil facesUtil;

	private Site site;
	private List<Site> otherSites;

	@PostConstruct
	public void init() {
		initSite();
		initOtherSites();
	}

	private void initOtherSites() {
		otherSites = new ArrayList<Site>(siteService.getSites());
		otherSites.remove(site);
	}

	private void initSite() {
		String siteFriendlyUrl = facesUtil.getParam("s");
		site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
		if (site == null) {
			site = siteService.getDefaultSite();
		}
	}

	public List<Site> getOtherSites() {
		return otherSites;
	}

	public Site getSite() {
		return site;
	}

}
