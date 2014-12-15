package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.service.RoleService;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Scope("session")
public class RoleBean {

	@Inject private RoleService roleService;

	private List<Role> roles;

	@PostConstruct
	public void init() {
		roles = roleService.getRoles();
	}

	public List<Role> getRoles() {
		return roles;
	}

}
