package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.service.ThemeService;
import be.webfactor.sitecubes.util.BeanLocator;

import javax.faces.FacesException;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
				File file = File.createTempFile("dynamic-", ".xhtml");

				Writer writer = null; sour
				try {
					writer = new FileWriter(file);
					writer.append(BeanLocator.getBean(ThemeService.class).getThemes().get(0).getTemplate());
				} finally {
					if (writer != null) {
						writer.close();
					}
				}

				final URL url = file.toURI().toURL();
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
