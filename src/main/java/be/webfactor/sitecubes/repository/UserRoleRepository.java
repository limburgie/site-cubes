package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Role;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.domain.User;
import be.webfactor.sitecubes.domain.UserRole;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

	@Cacheable("user_role")
	@Query("SELECT COUNT(*) FROM UserRole WHERE user=?1 AND site IS NULL AND role=?2")
	long getUserWithRoleCount(User user, Role role);

	@Cacheable("user_role")
	@Query("SELECT COUNT(*) FROM UserRole WHERE user=?1 AND site=?2 AND role=?3")
	long getUserWithSiteRoleCount(User user, Site site, Role role);

	@CacheEvict(value = "user_role", allEntries = true)
	UserRole save(UserRole userRole);

	@Cacheable("user_role")
	List<UserRole> findAll();

	@CacheEvict(value = "user_role", allEntries = true)
	void delete(UserRole userRole);

}
