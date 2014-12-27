package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

	@Cacheable("site")
	List<Site> findAll();

	@Cacheable("site")
	Site findOne(Long id);

	@Cacheable("site")
	@Query("FROM Site WHERE defaultSite=true")
	Site getDefaultSite();

	@CacheEvict(value = "site", allEntries = true)
	Site save(Site site);

	@CacheEvict(value = "site", allEntries = true)
	void delete(Site site);

	@Cacheable("site")
	@Query("FROM Site WHERE friendlyUrl=?1")
	Site findByFriendlyUrl(String friendlyUrl);

	@Cacheable("site")
	@Query("FROM Site WHERE virtualHost IS NOT NULL AND virtualHost=?1")
	Site findByVirtualHost(String virtualHost);

	@Cacheable("site")
	@Query("FROM Site WHERE name=?1")
	Site findByName(String name);
}
