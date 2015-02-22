package be.webfactor.sitecubes.domain;

import javax.persistence.*;

@Entity
public class PageLayout extends BaseEntity {

	@Column(name = "name", unique = true)
	private String name;

	@Column(name = "structure", nullable = false) @Lob
	private String structure;

	@Column(name = "default_layout")
	private boolean defaultLayout;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public boolean isDefaultLayout() {
		return defaultLayout;
	}

	public void setDefaultLayout(boolean defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

}
