package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.faces.helper.FacesUtil;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import java.io.IOException;

@Named @Scope("request")
public class LoginBean {

	@Inject private FacesUtil facesUtil;

	private String name;
	private String password;
	private boolean rememberMe;

	public void login() throws ServletException, IOException {
		facesUtil.forwardTo("/j_spring_security_check");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

}
