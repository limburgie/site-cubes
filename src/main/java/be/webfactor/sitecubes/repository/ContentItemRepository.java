package be.webfactor.sitecubes.repository;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.Site;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContentItemRepository extends JpaRepository<ContentItem, Long> {

	@Cacheable("content_item")
	@Query("FROM ContentItem WHERE site=?1")
	List<ContentItem> getSiteContent(Site site);

	@CacheEvict(value = {"content_item", "content_location"}, allEntries = true)
	ContentItem save(ContentItem item);

	@CacheEvict(value = {"content_item", "content_location"}, allEntries = true)
	void delete(ContentItem item);

	@Cacheable("content_item")
	@Query("FROM ContentItem WHERE site=?1 AND title=?2")
	ContentItem getItemByTitle(Site site, String title);

	@Cacheable("content_item")
	ContentItem findOne(Long id);
}
