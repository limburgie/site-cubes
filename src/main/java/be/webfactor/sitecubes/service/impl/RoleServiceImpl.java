package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.repository.RoleRepository;
import be.webfactor.sitecubes.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

@Named @Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

	@Inject private RoleRepository roleRepository;

	@PostConstruct
	@Transactional
	public void createDefaultRoles() {
		for (Role role : Role.DEFAULT_ROLES) {
			Role dbRole = getRoleByName(role.getName());
			if (dbRole == null) {
				save(role);
			}
		}
	}

	public Role getRoleByName(String name) {
		return roleRepository.findByName(name);
	}

	@Transactional
	public Role save(Role role) {
		return roleRepository.save(role);
	}

}
