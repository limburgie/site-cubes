package be.webfactor.sitecubes.rewrite;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.SiteService;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.InboundRewrite;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SiteVirtualHostDefaultPageOperation extends SiteCubesOperation {

	@Inject private SiteService siteService;
	@Inject private PageService pageService;

	public void performInbound(InboundRewrite event, EvaluationContext context) {
		Site site = siteService.getSiteByVirtualHost(getHostName(event));
		if (site != null) {
			Page page = pageService.getFirstPage(site);
			if (page != null) {
				forward(event, site.getFriendlyUrl(), page.getFriendlyUrl());
			}
		}
	}

}
