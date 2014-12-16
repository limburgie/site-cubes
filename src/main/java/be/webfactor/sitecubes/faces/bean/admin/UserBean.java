package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.UserService;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named @Scope("view")
public class UserBean implements Serializable {

	@Inject private UserService userService;
	@Inject private FacesUtil facesUtil;

	private User user;
	private List<User> users;

	@PostConstruct
	public void initData() {
		user = null;
		users = userService.getUsers();
	}

	public void initNewUser() {
		user = new User();
	}

	public void onRowSelect(SelectEvent event) {
		user = SerializationUtils.clone((User) event.getObject());
	}

	public void save() {
		userService.save(user);
		facesUtil.info("user-saved-successfully");
	}

	public void delete() {
		userService.delete(user);
		initData();
		facesUtil.info("user-deleted-successfully");
	}

	public void cancel() {
		initData();
	}

	public List<User> getUsers() {
		return users;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
