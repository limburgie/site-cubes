package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Theme;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

	@Cacheable("theme")
	List<Theme> findAll();

	@Cacheable("theme") @Query("FROM Theme WHERE name=?1")
	Theme findByName(String name);

	@CacheEvict(value = "theme", allEntries = true)
	Theme save(Theme theme);

	@CacheEvict(value = "theme", allEntries = true)
	void delete(Theme theme);

}
