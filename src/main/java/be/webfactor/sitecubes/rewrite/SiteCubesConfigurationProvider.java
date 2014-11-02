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

	private InboundPageOperation operation;

	public SiteCubesConfigurationProvider() {
		operation = BeanLocator.getBean(InboundPageOperation.class);
	}

	@Override
	public Configuration getConfiguration(ServletContext servletContext) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/login").to("/pages/login.xhtml"))
				.addRule(Join.path("/admin").to("/pages/admin/pages.xhtml"))
				.addRule(Join.path("/admin/{cp_item}").to("/pages/admin/{cp_item}.xhtml"))
				.addRule().when(Path.matches("/{siteFriendlyUrl}/{pageFriendlyUrl}")).perform(operation)
				.addRule().when(Path.matches("/{siteFriendlyUrl}")).perform(operation);
	}

	@Override
	public int priority() {
		return 10;
	}

}