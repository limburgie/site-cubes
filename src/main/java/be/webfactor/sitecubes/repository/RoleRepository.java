package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Role;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Cacheable("role")
	@Query("FROM Role WHERE name=?1")
	Role findByName(String name);

	@CacheEvict(value = "role", allEntries = true)
	Role save(Role role);

}
