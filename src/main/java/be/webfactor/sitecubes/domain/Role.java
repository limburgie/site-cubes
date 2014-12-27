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

	public static final String ADMIN_ROLE_NAME = "Administrator";
	public static final String SITE_ADMIN_ROLE_NAME = "Site Administrator";

	static {
		ADMINISTRATOR = new Role();
		ADMINISTRATOR.setName(ADMIN_ROLE_NAME);
		ADMINISTRATOR.setType(RoleType.GLOBAL);

		SITE_ADMINISTRATOR = new Role();
		SITE_ADMINISTRATOR.setName(SITE_ADMIN_ROLE_NAME);
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
