package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Role;

public interface RoleService {

	Role getRoleByName(String name);

	Role save(Role role);

}
