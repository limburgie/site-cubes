package be.webfactor.sitecubes.rewrite;


import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.util.BeanLocator;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.InboundOperation;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.InboundRewrite;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.param.Constraint;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.event.InboundServletRewrite;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class SiteCubesConfigurationProvider extends HttpConfigurationProvider {

	@Override
	public Configuration getConfiguration(ServletContext servletContext) {
		final PageService pageService = BeanLocator.getBean(PageService.class);
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/admin/{cp_item}").to("/pages/admin/{cp_item}.jsf"))
				.addRule().when(Path.matches("/")).perform(new InboundOperation() {
					public void performInbound(InboundRewrite event, EvaluationContext context) {
						Page first = pageService.getFirstPage();
						if (first != null) {
							((InboundServletRewrite) event).forward("/pages/view.jsf?u="+first.getFriendlyUrl());
						}
					}
				})
				.addRule().when(Path.matches("/{friendlyUrl}")).perform(Forward.to("/pages/view.jsf?u={friendlyUrl}")).where("friendlyUrl").matches(".*").constrainedBy(new Constraint<String>() {
					public boolean isSatisfiedBy(Rewrite event, EvaluationContext context, String friendlyUrl) {
						// Check if there is a page with this friendly URL
						if (friendlyUrl.contains("javax.faces.resource")) {
							return false;
						}
						return pageService.getPageByFriendlyUrl(friendlyUrl) != null;
					}
				});
	}

	@Override
	public int priority() {
		return 10;
	}

}