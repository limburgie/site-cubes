package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.domain.UserRole;
import be.webfactor.sitecubes.service.UserRoleService;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class UserRoleBean implements Serializable {

	@Inject private UserRoleService userRoleService;

	private User user;
	private List<UserRole> roles;
	private UserRole userRole;

	public void initNewRole() {
		userRole = new UserRole();
		userRole.setUser(user);
	}

	public void reset() {
		roles = null;
	}

	public void init(User user) {
		this.user = user;
		roles = userRoleService.getRoles(user);
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public List<UserRole> getRoles() {
		return roles;
	}

}
