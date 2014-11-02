package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Site extends BaseEntity {

	private static final String DEFAULT_SITE_NAME = "Website";
	private static final String DEFAULT_SITE_URL = "site";

	public static final Site DEFAULT_SITE;

	static {
		DEFAULT_SITE = new Site();
		DEFAULT_SITE.setName(DEFAULT_SITE_NAME);
		DEFAULT_SITE.setFriendlyUrl(DEFAULT_SITE_URL);
		DEFAULT_SITE.setDefaultSite(true);
	}

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Column(name = "friendly_url", nullable = false, unique = true)
	private String friendlyUrl;

	@Column(name = "virtual_host", unique = true)
	private String virtualHost;

	@Column(name = "default_site")
	private boolean defaultSite;

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

	public String getVirtualHost() {
		return virtualHost;
	}

	public void setVirtualHost(String virtualHost) {
		this.virtualHost = virtualHost;
	}

	public boolean isDefaultSite() {
		return defaultSite;
	}

	public void setDefaultSite(boolean defaultSite) {
		this.defaultSite = defaultSite;
	}

}
