package be.webfactor.sitecubes.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Page extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "friendly_url", nullable = false, unique = true)
	private String friendlyUrl;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_id", nullable = true)
	private Page parent;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Page> children = new ArrayList<Page>();

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

	public Page getParent() {
		return parent;
	}

	public void setParent(Page parent) {
		this.parent = parent;
	}

	public List<Page> getChildren() {
		return children;
	}

	public void setChildren(List<Page> children) {
		this.children = children;
	}

	public void addPage(Page page) {
		children.add(page);
		page.setParent(this);
	}

	public void removePage(Page page) {
		if (page.getParent() != null) {
			page.getParent().children.remove(page);
		}
		page.setParent(null);
	}

}
