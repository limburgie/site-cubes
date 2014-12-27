package be.webfactor.sitecubes.faces.converter;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.service.UserService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserConverter implements Converter {

	@Inject private UserService userService;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		long id = Long.valueOf(value);
		return userService.getUser(id);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		User user = (User) value;
		return String.valueOf(user.getId());
	}

}
