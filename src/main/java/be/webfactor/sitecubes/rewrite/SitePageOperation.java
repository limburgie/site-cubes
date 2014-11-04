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
public class SitePageOperation extends SiteCubesOperation {

	@Inject private SiteService siteService;
	@Inject private PageService pageService;

	public void performInbound(InboundRewrite event, EvaluationContext context) {
		String siteFriendlyUrl = getSiteFriendlyUrl(context);
		String pageFriendlyUrl = getPageFriendlyUrl(context);

		Site site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
		if (site != null) {
			Page page = pageService.getPageByFriendlyUrl(site, pageFriendlyUrl);
			if (page != null) {
				forward(event, siteFriendlyUrl, pageFriendlyUrl);
			}
		}
	}

}
