package be.webfactor.sitecubes.rewrite;


import be.webfactor.sitecubes.util.BeanLocator;
import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.Path;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

@RewriteConfiguration
public class SiteCubesConfigurationProvider extends HttpConfigurationProvider {

	private SitePageOperation sitePageOperation;
	private SiteDefaultPageOperation siteDefaultPageOperation;
	private SiteVirtualHostPageOperation siteVirtualHostPageOperation;
	private SiteVirtualHostDefaultPageOperation siteVirtualHostDefaultPageOperation;

	public SiteCubesConfigurationProvider() {
		sitePageOperation = BeanLocator.getBean(SitePageOperation.class);
		siteDefaultPageOperation = BeanLocator.getBean(SiteDefaultPageOperation.class);
		siteVirtualHostPageOperation = BeanLocator.getBean(SiteVirtualHostPageOperation.class);
		siteVirtualHostDefaultPageOperation = BeanLocator.getBean(SiteVirtualHostDefaultPageOperation.class);
	}

	@Override
	public Configuration getConfiguration(ServletContext servletContext) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/file/{siteFriendlyUrl}/{fileName}").to("/file?s={siteFriendlyUrl}&f={fileName}"))
				.addRule(Join.path("/login").to("/pages/login.xhtml"))
				.addRule(Join.path("/admin").to("/pages/admin/pages.xhtml"))
				.addRule(Join.path("/admin/{cp_item}").to("/pages/admin/{cp_item}.xhtml"))
				.addRule().when(Path.matches("/{siteFriendlyUrl}/{pageFriendlyUrl}")).perform(sitePageOperation)
				.addRule().when(Path.matches("/{siteFriendlyUrl}")).perform(siteDefaultPageOperation)
				.addRule().when(Path.matches("/{pageFriendlyUrl}")).perform(siteVirtualHostPageOperation)
				.addRule().when(Path.matches("/")).perform(siteVirtualHostDefaultPageOperation);
	}

	@Override
	public int priority() {
		return 10;
	}

}