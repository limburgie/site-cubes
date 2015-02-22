package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Theme;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

	@Cacheable("theme")
	Theme findOne(Long id);

	@Cacheable("theme")
	List<Theme> findAll();

	@CacheEvict(value = {"theme", "site", "page"}, allEntries = true)
	Theme save(Theme theme);

	@CacheEvict(value = "theme", allEntries = true)
	void delete(Theme theme);

	@Cacheable("theme") @Query("FROM Theme WHERE defaultTheme=true")
	Theme getDefault();

}
