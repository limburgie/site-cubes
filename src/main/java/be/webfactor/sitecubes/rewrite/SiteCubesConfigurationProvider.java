package be.webfactor.sitecubes.rewrite;


import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.SiteService;
import be.webfactor.sitecubes.util.BeanLocator;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.InboundOperation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.InboundRewrite;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.event.InboundServletRewrite;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class SiteCubesConfigurationProvider extends HttpConfigurationProvider {

	private PageService pageService;
	private SiteService siteService;

	public SiteCubesConfigurationProvider() {
		pageService = BeanLocator.getBean(PageService.class);
		siteService = BeanLocator.getBean(SiteService.class);
	}

	@Override
	public Configuration getConfiguration(ServletContext servletContext) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/login").to("/pages/login.xhtml"))
				.addRule(Join.path("/admin").to("/admin/pages").withChaining())
				.addRule(Join.path("/admin/{cp_item}").to("/pages/admin/{cp_item}.xhtml"))
				.addRule().when(Path.matches("/{siteFriendlyUrl}")).perform(new InboundOperation() {
					public void performInbound(InboundRewrite event, EvaluationContext context) {
						String siteFriendlyUrl = (String) context.get("siteFriendlyUrl");
						Site site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
						if (site != null) {
							Page first = pageService.getFirstPage(site);
							if (first != null) {
								((InboundServletRewrite) event).forward("/" + siteFriendlyUrl + "/" + first.getFriendlyUrl());
							}
						}
					}
				})
				.addRule().when(Path.matches("/{siteFriendlyUrl}/{pageFriendlyUrl}")).perform(new InboundOperation() {
					public void performInbound(InboundRewrite event, EvaluationContext context) {
						String siteFriendlyUrl = (String) context.get("siteFriendlyUrl");
						String pageFriendlyUrl = (String) context.get("pageFriendlyUrl");

						if (siteFriendlyUrl == null || pageFriendlyUrl == null || pageFriendlyUrl.contains("javax.faces.resource")) {
							return;
						}

						Site site = siteService.getSiteByFriendlyUrl(siteFriendlyUrl);
						if (site != null) {
							Page page = pageService.getPageByFriendlyUrl(site, pageFriendlyUrl);
							if (page != null) {
								Forward.to("/pages/view.xhtml?s="+siteFriendlyUrl+"&p="+pageFriendlyUrl);
							}
						}
					}
				});
	}


	@Override
	public int priority() {
		return 10;
	}

}