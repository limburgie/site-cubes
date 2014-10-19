package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.SiteService;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Scope("view")
public class SiteBean {

	@Inject private SiteService siteService;
	@Inject private FacesUtil facesUtil;

	private List<Site> sites;
	private Site site;

	@PostConstruct
	public void initData() {
		site = null;
		sites = siteService.getSites();
	}

	public void initNewSite() {
		site = new Site();
	}

	public void onRowSelect(SelectEvent event) {
		site = SerializationUtils.clone((Site) event.getObject());
	}

	public void save() {
		siteService.save(site);
		facesUtil.info("site-saved-successfully");
	}

	public void delete() {
		siteService.delete(site);
		initData();
		facesUtil.info("site-deleted-successfully");
	}

	public void cancel() {
		initData();
	}

	public List<Site> getSites() {
		return sites;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}
