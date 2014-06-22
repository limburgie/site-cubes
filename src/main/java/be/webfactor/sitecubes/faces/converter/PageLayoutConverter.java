package be.webfactor.sitecubes.faces.converter;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.service.PageLayoutService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class PageLayoutConverter implements Converter {

	@Inject private PageLayoutService pageLayoutService;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		long id = Long.valueOf(value);
		return pageLayoutService.getLayout(id);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		PageLayout layout = (PageLayout) value;
		return String.valueOf(layout.getId());
	}

}
