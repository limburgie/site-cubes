package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentLocationRepository extends JpaRepository<ContentLocation, Long> {

	@CacheEvict(value = "content_location", allEntries = true)
	ContentLocation save(ContentLocation location);

	@CacheEvict(value = "content_location", allEntries = true)
	ContentLocation saveAndFlush(ContentLocation location);

	@Cacheable("content_location")
	ContentLocation findOne(Long id);

	@CacheEvict(value = "content_location", allEntries = true)
	void delete(ContentLocation location);

	@Cacheable("content_location")
	@Query("FROM ContentLocation WHERE page=?1 ORDER BY position ASC")
	List<ContentLocation> findByPage(Page page);

	@Cacheable("content_location")
	@Query("FROM ContentLocation WHERE page=?1 AND columnId=?2 AND position>=?3 ORDER BY position ASC")
	List<ContentLocation> findByPageAndColumnIdFromPosition(Page page, String columnId, int position);

	@CacheEvict(value = "content_location", allEntries = true)
	@Modifying @Query("DELETE FROM ContentLocation WHERE item=?1")
	void deleteItemLocations(ContentItem item);

	@CacheEvict(value = "content_location", allEntries = true)
	@Modifying @Query("DELETE FROM ContentLocation WHERE page=?1")
	void deletePageLocations(Page page);

	@Cacheable("content_location")
	@Query("SELECT COUNT(*) FROM ContentLocation WHERE page=?1 AND columnId=?2")
	int getLocationCountForPageInColumnId(Page page, String columnId);

	@CacheEvict(value = "content_location", allEntries = true)
	@Modifying @Query("DELETE FROM ContentLocation WHERE page.site=?1")
	void deleteSiteLocations(Site site);

}
