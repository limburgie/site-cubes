package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.UserRole;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.UserRoleService;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class UserRoleBean implements Serializable {

	@Inject private UserRoleService userRoleService;
	@Inject private FacesUtil facesUtil;

	private List<UserRole> userRoles;
	private UserRole userRole;

	@PostConstruct
	public void initData() {
		userRole = null;
		userRoles = userRoleService.getUserRoles();
	}

	public void initNewUserRole() {
		userRole = new UserRole();
	}

	public void onRowSelect(SelectEvent event) {
		userRole = SerializationUtils.clone((UserRole) event.getObject());
	}

	public void save() {
		userRoleService.save(userRole);
		facesUtil.info("role-assignment-saved-successfully");
	}

	public void delete() {
		userRoleService.delete(userRole);
		initData();
		facesUtil.info("role-assignment-deleted-successfully");
	}

	public void cancel() {
		initData();
	}

	public List<UserRole> getUserRoles() {
		return userRoles;
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

}
