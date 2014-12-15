package be.webfactor.sitecubes.faces.converter;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.service.RoleService;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class RoleConverter implements Converter {

	@Inject private RoleService roleService;

	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		long id = Long.valueOf(value);
		return roleService.getRole(id);
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		Role role = (Role) value;
		return String.valueOf(role.getId());
	}

}
