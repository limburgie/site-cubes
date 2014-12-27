package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Role;

import java.util.List;

public interface RoleService {

	Role getRoleByName(String name);

	Role save(Role role);

	Role getRole(long id);

	List<Role> getRoles();

}
