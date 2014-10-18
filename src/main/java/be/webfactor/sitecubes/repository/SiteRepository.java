package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteRepository extends JpaRepository<Site, Long> {

	@Cacheable("site")
	List<Site> findAll();

}
