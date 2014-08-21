package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentLocationRepository extends JpaRepository<ContentLocation, Long> {

	@Cacheable("content_location")
	@Query("FROM ContentLocation WHERE page=?1")
	List<ContentLocation> findByPage(Page page);

	@CacheEvict(value = "content_location", allEntries = true)
	@Modifying @Query("DELETE FROM ContentLocation WHERE item=?1")
	void deleteItemLocations(ContentItem item);

}
