package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Theme extends BaseEntity {

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "template", nullable = false) @Lob
	private String template;

	@Column(name = "css") @Lob
	private String css;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public String getCss() {
		return css;
	}

	public void setCss(String css) {
		this.css = css;
	}

}
