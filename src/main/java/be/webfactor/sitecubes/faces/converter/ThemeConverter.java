package be.webfactor.sitecubes.faces.converter;

import be.webfactor.sitecubes.domain.Theme;
import be.webfactor.sitecubes.service.ThemeService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class ThemeConverter implements Converter {

	@Inject private ThemeService themeService;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		long id = Long.valueOf(value);
		return themeService.getTheme(id);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Theme theme = (Theme) value;
		return String.valueOf(theme.getId());
	}

}
