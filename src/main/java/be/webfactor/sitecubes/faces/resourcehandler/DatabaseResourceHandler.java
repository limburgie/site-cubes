package be.webfactor.sitecubes.faces.resourcehandler;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.SiteService;
import be.webfactor.sitecubes.util.BeanLocator;

import javax.faces.FacesException;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.net.URL;

public class DatabaseResourceHandler extends ResourceHandlerWrapper {

	private ResourceHandler wrapped;

	public DatabaseResourceHandler(ResourceHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ViewResource createViewResource(FacesContext context, String resourceName) {
		if (resourceName.equals("/pages/view.xhtml")) {
			try {
				String siteFriendlyUrl = BeanLocator.getBean(FacesUtil.class).getParam("s");
				Site site = BeanLocator.getBean(SiteService.class).getSiteByFriendlyUrl(siteFriendlyUrl);
				String template = site.getTheme().getTemplate();
				final URL url = new URL(null, "sample://test?t=" + System.currentTimeMillis(), new CustomURLStreamHandler(template));

				return new ViewResource() {
					@Override
					public URL getURL() {
						return url;
					}
				};

			} catch (IOException e) {
				throw new FacesException(e);
			}
		}

		return super.createViewResource(context, resourceName);
	}

	public ResourceHandler getWrapped() {
		return wrapped;
	}

}
