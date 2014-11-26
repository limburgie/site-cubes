package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	@Cacheable("user")
	List<User> findAll();

	@CacheEvict(value = "user", allEntries = true)
	void delete(User user);

	@CacheEvict(value = "user", allEntries = true)
	User save(User user);

}
