package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Scanner;

@Entity
public class Theme extends BaseEntity {

	public static final Theme DEFAULT;

	private static final String DEFAULT_NAME = "Default theme";
	private static final String DEFAULT_TEMPLATE_LOCATION = "/default-theme.xhtml";

	static {
		DEFAULT = new Theme();
		DEFAULT.setName(DEFAULT_NAME);
		DEFAULT.setTemplate(new Scanner(Theme.class.getResourceAsStream(DEFAULT_TEMPLATE_LOCATION), "UTF-8").useDelimiter("\\A").next());
		DEFAULT.setCss("");
	}

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "template", length = 1024 * 1024, nullable = false) @Lob
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
