package be.webfactor.sitecubes.domain;

import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UQ_PARENT_POSITION", columnNames = {"parent_id", "position"}),
		@UniqueConstraint(name = "UQ_SITE_FRIENDLY_URL", columnNames = {"site_id", "friendly_url"})
})
public class Page extends BaseEntity {

	public static final String ROOT_NAME = "Root";
	public static final String ROOT_FRIENDLY_URL = "/";

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "friendly_url", nullable = false)
	private String friendlyUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	private Page parent;

	@Column(name = "position", nullable = false)
	private int position;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
	@OrderBy("position ASC")
	private List<Page> children = new ArrayList<Page>();

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_layout_id", nullable = false)
	private PageLayout layout;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;

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

	@Cacheable("page")
	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Cacheable("page")
	public List<Page> getChildren() {
		return children;
	}

	public void setChildren(List<Page> children) {
		this.children = children;
	}

	@Cacheable("page")
	public PageLayout getLayout() {
		return layout;
	}

	public void setLayout(PageLayout layout) {
		this.layout = layout;
	}

	@Cacheable("site")
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

	public void removePage(Page page) {
		if (page.getParent() != null) {
			page.getParent().children.remove(page);
		}
		page.setParent(null);
	}

	@Override
	public String toString() {
		return friendlyUrl;
	}

}
