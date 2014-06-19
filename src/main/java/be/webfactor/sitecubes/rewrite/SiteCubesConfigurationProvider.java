package be.webfactor.sitecubes.rewrite;


import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.util.BeanLocator;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.event.Rewrite;
import org.ocpsoft.rewrite.param.Constraint;
import org.ocpsoft.rewrite.servlet.config.Forward;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class SiteCubesConfigurationProvider extends HttpConfigurationProvider {

	@Override
	public Configuration getConfiguration(ServletContext servletContext) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/admin/pages").to("/pages/admin/pages.jsf"))
				.addRule().when(Path.matches("/{friendlyUrl}")).perform(Forward.to("/pages/view.jsf?u={friendlyUrl}")).where("friendlyUrl").matches(".*").constrainedBy(new Constraint<String>() {
					public boolean isSatisfiedBy(Rewrite event, EvaluationContext context, String value) {
						// Check if there is a page with this friendly URL
						Page page = BeanLocator.getBean(PageService.class).getPageByFriendlyUrl(value);
						return page != null;
					}
				});
	}

	@Override
	public int priority() {
		return 10;
	}

}