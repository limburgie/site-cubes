package be.webfactor.sitecubes.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Arrays;
import java.util.List;

@Entity
public class Role extends BaseEntity {

	private static final Role ADMINISTRATOR;
	private static final Role SITE_ADMINISTRATOR;
	public static final List<Role> DEFAULT_ROLES;

	static {
		ADMINISTRATOR = new Role();
		ADMINISTRATOR.setName("Administrator");
		ADMINISTRATOR.setType(RoleType.GLOBAL);

		SITE_ADMINISTRATOR = new Role();
		SITE_ADMINISTRATOR.setName("Site Administrator");
		SITE_ADMINISTRATOR.setType(RoleType.SITE);

		DEFAULT_ROLES = Arrays.asList(ADMINISTRATOR, SITE_ADMINISTRATOR);
	}

	@Column(name = "name", nullable = false, unique = true)
	private String name;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "type", nullable = false)
	private RoleType type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoleType getType() {
		return type;
	}

	public void setType(RoleType type) {
		this.type = type;
	}

}
