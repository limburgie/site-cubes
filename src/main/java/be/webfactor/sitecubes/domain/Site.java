package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Site extends BaseEntity {

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "friendly_url", nullable = false, unique = true)
	private String friendlyUrl;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFriendlyUrl() {
		return friendlyUrl;
	}

	public void setFriendlyUrl(String friendlyUrl) {
		this.friendlyUrl = friendlyUrl;
	}

}
