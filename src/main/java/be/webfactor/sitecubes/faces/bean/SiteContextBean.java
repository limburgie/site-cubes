package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.SiteService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named @Scope("session")
public class SiteContextBean implements Serializable {

	@Inject private SiteService siteService;

	private Site activeSite;

	@PostConstruct
	public void init() {
		activeSite = siteService.getDefaultSite();
	}

	public Site getActiveSite() {
		return activeSite;
	}

	public void setActiveSite(Site activeSite) {
		this.activeSite = activeSite;
	}

}
