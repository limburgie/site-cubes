package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends BaseEntity {

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
