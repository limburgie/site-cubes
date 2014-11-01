package be.webfactor.sitecubes.rewrite;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.SiteService;
import org.ocpsoft.rewrite.bind.Evaluation;
import org.ocpsoft.rewrite.config.InboundOperation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.InboundRewrite;
import org.ocpsoft.rewrite.servlet.event.InboundServletRewrite;

import javax.inject.Inject;
import javax.inject.Named;

@Named
public class InboundPageOperation extends InboundOperation {

	@Inject private SiteService siteService;
	@Inject private PageService pageService;

	public void performInbound(InboundRewrite event, EvaluationContext context) {
		String siteFriendlyUrl = getContextValue(context, "siteFriendlyUrl");
		String pageFriendlyUrl = getContextValue(context, "pageFriendlyUrl");

		if (siteFriendlyUrl == null || (pageFriendlyUrl != null && pageFriendlyUrl.contains("javax.faces.resource"))) {
			return;
		}

		Site site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
		if (site != null) {
			Page page;
			if (pageFriendlyUrl == null) {
				page = pageService.getFirstPage(site);
			} else {
				page = pageService.getPageByFriendlyUrl(site, pageFriendlyUrl);
			}
			if (page != null) {
				((InboundServletRewrite) event).forward("/pages/view.xhtml?s=" + siteFriendlyUrl + "&p=" + page.getFriendlyUrl());
			}
		}
	}

	private String getContextValue(EvaluationContext context, String key) {
		Object[] values = (Object[]) context.get(Evaluation.class.getName() + "_" + key);
		if (values != null && values.length > 0) {
			return (String) values[0];
		}
		return null;
	}

}
