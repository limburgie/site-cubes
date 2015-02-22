package be.webfactor.sitecubes.faces.converter;

import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.service.SiteService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class SiteConverter implements Converter {

	@Inject private SiteService siteService;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		long id = Long.valueOf(value);
		return siteService.getSite(id);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Site site = (Site) value;
		return String.valueOf(site.getId());
	}

}
